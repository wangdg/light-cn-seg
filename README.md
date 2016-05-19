LCS中文分词
==================

## 1.简单介绍

- LCS = Light Chinese Segmenter。
- 简单的中文分词器。

## 2.如何使用

### 2.1 使用默认词典分词

```
// 创建分词器
ISegmenter segmenter = new FMMSegmenter();
// 分词得到结果
List<TermData> segs = segmenter.analyze("测试文字");
...

```

### 2.2 使用自定义词典分词

```
// 指定词典来源
IDictionaryDataSource source = new DictionaryFileDataSource(new File("/main.txt"));
// 创建分词器
ISegmenter segmenter = new FMMSegmenter(source);
// 分词得到结果
List<TermData> segs = segmenter.analyze("测试文字");
...

```