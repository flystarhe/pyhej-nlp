# pyAudioAnalysis

## Installation
```
apt-get install ffmpeg
pip install numpy matplotlib scipy sklearn hmmlearn simplejson eyed3 pydub
```

add submodule:
```
git submodule add https://github.com/tyiannak/pyAudioAnalysis.git modules/audio_analysis
git submodule add https://github.com/ksingla025/pyAudioAnalysis3.git modules/audio_analysis3
```

## Perform unsupervised segmentation(Speaker Diarization)
[doc](https://github.com/tyiannak/pyAudioAnalysis/wiki/5.-Segmentation#speaker-diarization).
```python
import sys

this_lib = '/data2/tmps/pyhej-nlp/modules'
if this_lib not in sys.path:
    sys.path.insert(0, this_lib)

from audio_analysis3 import audioSegmentation
help(audioSegmentation.speakerDiarization)
```
