1. 运行 com.avaw.parsesubtitle.ParseSubtitle Java程序,解析字幕文件src保存至数据库word表
   运行后得到指定字幕的所有单词并保存到表 words 中

2. 整理word表中的单词(已学会的、各类时态ed,ing、人名、短语等)
   过去时、现在进行时  select * from words where word like '%ed' or word like '%ing';

3. 执行SQL语句,并精简单词表
   3.1 create table tempwords  select * from words order by length(word);
   3.2 得到tempwords表,删除已熟悉的单词(比较短的单词还是比较熟悉的)
   3.3 insert into words(word,status) select word,1 from tempwords order by id;
   3.4 检查words表，确认单词

4. com.avaw.parsesubtitle.DictWords     调用翻译网站获取每个单词的音标、例句等



附1: MVK文件导出字幕src方法
  ffmpeg -i source.mkv -an -vn -c:s:0.2 srt sub.srt 
  
  注意 s:0.2 参数,需要对应相关的字幕语言(看ffmpeg输出的信息来决定)

附2: MVK文件导出字幕src方法
  ffmpeg -i source.mkv     -vn -c:a:0.2 libmp3lame audio.mp3

附3: Word表结构： 

CREATE TABLE `words` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `word` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '单词',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `pron` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '音标',
  `chinese` varchar(255) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '中文',
  `example` text COLLATE utf8_unicode_ci COMMENT '例句',
  `memo` text COLLATE utf8_unicode_ci COMMENT '备注',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `voice` blob COMMENT '语音',
  `source` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '出处',
  PRIMARY KEY (`id`),
  UNIQUE KEY `word` (`word`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

附4: 精简word表后可使用如下sql来重新整理ID字段
  insert into words(word,status,pron,chinese,example,memo,type,voice,source) select word,status,pron,chinese,example,memo,type,voice,source from words_friends_S01E01 where status-1 order by id;

附5: 随机取一条记录
  select * from words where status=0  order by rand() limit 1 