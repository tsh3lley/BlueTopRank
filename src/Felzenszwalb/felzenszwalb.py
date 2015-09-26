from skimage.segmentation import felzenszwalb
import matplotlib.pyplot as plt
from scipy import misc


pattern = misc.imread('C:\Users\Thomas\workspacePy\opencv.jpg')

pattern = felzenszwalb(pattern, scale=100, sigma=0.5, min_size=50)

misc.imsave('plsuguys.jpg', pattern)
plt.imshow(pattern)