LCS中文分词
==================

## 简单介绍

- LCS = Light Chinese Segmenter。
- 简单的中文分词器。

## 如何使用

```
// 创建分词器
ISegmenter segmenter = new FMMSegmenter();
// 分词得到结果
List<TermData> segs = segmenter.analyze("测试文字");
...

```

