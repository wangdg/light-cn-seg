LCS中文分词
==================

## 1.简单介绍

- LCS = Lightweight Chinese Segmenter。
- 简单的中文分词器。

## 2.如何使用

### 2.1 导入Jar

```
<repositories>
    <repository>
        <id>sonatype</id>
        <name>sonatype</name>
        <url>https://oss.sonatype.org/content/groups/staging</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.wangdg</groupId>
        <artifactId>light-cn-seg</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```


### 2.2 使用默认词典分词

```
// 创建分词器
ISegmenter segmenter = new FMMSegmenter();
// 分词得到结果
List<TermData> segs = segmenter.analyze("测试文字");
...

```

### 2.3 使用自定义词典分词

```
// 创建词典
LCSDictionary dict = new LCSDictionary(new File("/PATH/TO/dict.dic"));
// 创建分词器
ISegmenter segmenter = new FMMSegmenter(dict);
// 分词得到结果
List<TermData> segs = segmenter.analyze("测试文字");
...

```

## 3.Solr支持

### 3.1 Analyzer

支持的分析器(Analyzer)有：

- FMMAnalyzer
- RMMAnalyzer
- ICCAnalyzer

### 3.2 schema.xml：

```
<fieldType name="lcg_fmm" class="solr.TextField">
    <analyzer class="com.wangdg.lcs.solr.FMMAnalyzer" />
</fieldType>

<fieldType name="lcg_rmm" class="solr.TextField">
    <analyzer class="com.wangdg.lcs.solr.RMMAnalyzer" />
</fieldType>

<fieldType name="lcg_fmm" class="solr.TextField">
    <analyzer class="com.wangdg.lcs.solr.ICCAnalyzer" />
</fieldType>
```
