LCS中文分词
==================

## 简单介绍

- LCS = Light Chinese Segmenter。
- 简单的中文分词器。

## 如何使用

```
// 加载词典
IDictionaryDataSource source = new DictionaryFileDataSource(new File("/Users/wangdg/Documents/main.txt"));
Dictionary dict = new Dictionary(source);

// 创建分词器
ISegmenter segmenter = new FMMSegmenter(dict);

// 分词得到结果
List<TermData> segs = segmenter.analyze("测试文字");

...

```

