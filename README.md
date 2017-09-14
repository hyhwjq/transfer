# Transfer配置说明

## 内存和cpu使用率记录
```
<!-- 开启内存cpu使用记录，不配置不开启 -->
record.interval=10000
```

## 导出的数据库是Mongodb需配置
```
<!-- 源mongodb的url -->
source.mongo.url=192.168.1.1:27017
<!-- 源mongodb的用户名，为空不填写 -->
source.mongo.username=username
<!-- 源mongodb的密码，为空不填写 -->
source.mongo.password=password
<!-- 源mongodb的数据库 -->
source.mongo.db=db
```

## 导入的数据库是Mongodb需配置
```
<!-- 目的mongodb的url -->
dest.mongo.url=192.168.1.1:27017
<!-- 目的mongodb的用户名，为空不填写 -->
dest.mongo.username=
<!-- 目的mongodb的密码，为空不填写 -->
dest.mongo.password=
<!-- 目的mongodb的db -->
dest.mongo.db=db
```

## 导入的数据库是OSS需配置
```
<!-- 目的oss的endpoint -->
dest.oss.endpoint=oss-cn-hangzhou.aliyuncs.com
<!-- 目的oss的accessKeyId -->
dest.oss.accessKeyId=******
<!-- 目的oss的accessKeySecret -->
dest.oss.accessKeySecret=******
<!-- 目的oss的bucketName -->
dest.oss.bucket.name=bucketName
```

## 导出的数据库是Mongo的document需配置
```
<!-- 源mongo的collection, 多个用“,”分开逗号前后不能有空 -->
source.mongo.collections=collection1,collection2
```

## 导出的数据库是Mongo的GridFS需配置
```
<!-- 源mongo GridFS类型的collection, 多个用“,”分开逗号前后不能有空 -->
source.mongo.grid.fs.collections=collection1,collection2
<!-- 数据传输队列的大小，请考虑jvm设置的是否合理 -->
data.queue.size=100
```

## 运行模式
```
<!-- 暂时只支持两种模式 0: mongo2mongo, 1:mongoGridFS2OSS -->
run.mode=0
```