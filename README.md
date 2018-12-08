此项目是为了测试canal（基于数据库增量日志解析，提供增量数据订阅&消费）
目前主要支持了mysql，内部版本已经支持mysql和oracle部分版本的日志解析

（1）开启mysql的binlog功能，并配置binlog模式为row。
在my.cnf 加入如下：
[mysqld]
log-bin=mysql-bin #添加这一行就ok
binlog-format=ROW #选择row模式
server_id=1 #配置mysql replaction需要定义，不能和canal的slaveId重复

（2）在mysql中 配置canal数据库管理用户，配置相应权限（repication权限）
CREATE USER canal IDENTIFIED BY 'canal';
GRANT SELECT, REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'canal'@'%';
FLUSH PRIVILEGES;

注意：（1）（2）步执行完后一定要记得重启MySQL