# elasticsearch_5.1.2_test    
-- 更新原来的2.4.1版本的es 发现需要更新的东西很多，插件方面head插件的安装也不是很顺利，sql插件的安装也是显得麻烦，
-- 但是api的改变也不是很大，基本上原来的api都能用，除了script去除了groovy，新增了painless语言，使用起来也是差不多的，
-- 还有updatebyquery，deletebyquery，5.1.2都自带了，不用addPlugin的方式增加到client上，这点是很好的。
-- rest客户端还没接触，待研究
