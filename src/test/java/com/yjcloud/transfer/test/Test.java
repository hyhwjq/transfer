package com.yjcloud.transfer.test;

import com.yjcloud.transfer.core.dumper.MongoDumper;
import com.yjcloud.transfer.core.dumper.OSSDumper;
import com.yjcloud.transfer.core.loader.MongoFSLoader;
import com.yjcloud.transfer.core.loader.MongoLoader;

/**
 * Created by hhc on 17/9/13.
 */
public class Test {

    @org.junit.Test
    public void testMongo2Mongo() throws Exception {
        MongoLoader mongoLoader = new MongoLoader();
        mongoLoader.load();
        MongoDumper mongoDumper = new MongoDumper();
        mongoDumper.dump();

        while (true) {
            Thread.sleep(100000L);
        }

    }

    @org.junit.Test
    public void testMongoGridFS2OSS() throws Exception {
        MongoFSLoader mongoFSLoader = new MongoFSLoader();
        mongoFSLoader.load();

        OSSDumper ossDumper = new OSSDumper();
        ossDumper.dump();

        while (true) {
            Thread.sleep(100000L);
        }

    }

}
