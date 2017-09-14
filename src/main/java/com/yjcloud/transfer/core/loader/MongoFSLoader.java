package com.yjcloud.transfer.core.loader;

import com.google.common.base.Splitter;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.yjcloud.transfer.core.Loader;
import com.yjcloud.transfer.core.adapter.MongoAdapter;
import com.yjcloud.transfer.entity.MessageDTO;
import com.yjcloud.transfer.util.config.ConfigureEnum;
import com.yjcloud.transfer.util.config.PropertyConfigurer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hhc on 17/9/13.
 */
public class MongoFSLoader extends MongoAdapter implements Loader {
    private Long count = 0L;

    @Override
    protected void runTask() {
        LOG.info("mongo FS loader started.");
        DB db = new DB(mongoClient.getMongo(), mongoClient.getDatabase());

        String collectionNames = "";
        if (PropertyConfigurer.containsKey(ConfigureEnum.SOURCE_MONGO_GRID_FS_COLLECTIONS.getName())) {
            collectionNames = PropertyConfigurer.getProperty(ConfigureEnum.SOURCE_MONGO_GRID_FS_COLLECTIONS.getName());
        }

        Iterable<String> collections = Splitter.on(",").split(collectionNames);
        for (String collection : collections) {
            GridFS fs;
            if (StringUtils.isBlank(collectionNames)){
                fs = new GridFS(db);
            } else {
                fs = new GridFS(db, collection);
            }
            DBCursor fileList = fs.getFileList();

            while (fileList.hasNext()) {
                DBObject obj = fileList.next();

                GridFSDBFile dbFile = fs.findOne(obj);
                if (dbFile == null) {
                    continue;
                }
                int len = (int) dbFile.getLength();
                byte[] ret = new byte[len];
                InputStream is = dbFile.getInputStream();
                try {
                    IOUtils.read(is, ret);
                } catch (IOException e) {
                    LOG.error("read data err_{}", e.getMessage(), e);
                    continue;
                } finally {
                    IOUtils.closeQuietly(is);
                }
                String filename = dbFile.getFilename();

                this.pushData(new MessageDTO(filename, ret));
                count++;
            }
        }

        LOG.info("mongoFS loading completed. Article {} the load data ", count);
    }

    @Override
    public void load() {
        super.start();
    }

}
