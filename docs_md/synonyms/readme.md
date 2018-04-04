# Synonyms
Chinese Synonyms for Natural Language Processing and Understanding.

最好的中文近义词工具包.synonyms可以用于自然语言理解的很多任务:文本对齐,推荐算法,相似度计算,语义偏移,关键字提取,概念提取,自动摘要,搜索引擎等.

安装:
```bash
$ pip install -U synonyms
```

示例:
```python
import synonyms
synonyms.display('能量')
```

支持使用环境变量配置分词词表和word2vec词向量文件:

|环境变量                            |描述                                  |
|:----------------------------------|:------------------------------------|
|SYNONYMS_WORD2VEC_BIN_MODEL_ZH_CN  |使用word2vec训练的词向量文件,二进制格式   |
|SYNONYMS_WORDSEG_DICT              |中文分词主字典，格式和使用参考jieba       |

优雅的方式,代码前端添加:
```python
import os
os.environ['SYNONYMS_WORDSEG_DICT'] = 'home/hejian/data/dict.txt.big'
```

## seg
中文分词:
```python
import synonyms
synonyms.seg('中文近义词工具包')
```

分词结果,由两个list组成的元组,分别是单词和对应的词性:
```
(['中文', '近义词', '工具包'], ['nz', 'n', 'n'])
```

>该分词不去停用词和标点.

## nearby
近义词:
```python
import synonyms
synonyms.nearby('人脸')
```

## compare
两个句子的相似度比较:
```python
import synonyms
sen1 = '发生历史性变革'
sen2 = '发生历史性变革'
r = synonyms.compare(sen1, sen2, seg=True)
```

参数`seg`表示是否对`sen1`和`sen2`进行分词,默认为`True`.返回值`[0-1]`,并且越接近于1代表两个句子越相似.
```
旗帜引领方向 vs 道路决定命运: 0.429
旗帜引领方向 vs 旗帜指引道路: 0.93
发生历史性变革 vs 发生历史性变革: 1.0
```

## display
以友好的方式打印近义词,方便调试,`display`用了`nearby`方法:
```python
>>> synonyms.display('飞机')
'飞机'近义词：
  1. 架飞机:0.837399
  2. 客机:0.764609
  3. 直升机:0.762116
  4. 民航机:0.750519
  5. 航机:0.750116
  6. 起飞:0.735736
  7. 战机:0.734975
  8. 飞行中:0.732649
  9. 航空器:0.723945
  10. 运输机:0.720578
```
