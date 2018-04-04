## git

### add submodule
```
git submodule add https://github.com/tyiannak/pyAudioAnalysis.git modules/audio_analysis
git submodule add https://github.com/ksingla025/pyAudioAnalysis3.git modules/audio_analysis3
```

### push
```
git submodule update --remote
git add .
git commit -m "."
git push origin master
```

### pull
```
git clone https://***.git
git submodule init
git submodule update

git pull origin master
git submodule update --remote
```

## requirements
```
pip install hmmlearn simplejson eyed3
pip install pydub
pip install -U synonyms
```

## Synonyms
可以用于自然语言理解的很多任务:文本对齐,推荐算法,相似度计算,语义偏移,关键字提取,概念提取,自动摘要,搜索引擎等.
```
pip install -U synonyms
```

## Jpype1
实现Python调用Jar.
```
#Debian/Ubuntu:
sudo apt-get install g++ python3-dev
#Red Hat/Fedora:
su -c 'yum install gcc-c++ python3-devel'

conda install -c conda-forge jpype1
```
