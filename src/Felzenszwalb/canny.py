import numpy as np
from matplotlib import pyplot as plt
import cv2

jak = cv2.imread('workspacePy/jackAK.jpg')

img = cv2.cvtColor(jak, cv2.COLOR_BGR2GRAY)
cv2.imwrite('workspacePy/gray_image.png',img)

edges = cv2.Canny(img,125,25)
    
cv2.imwrite('workspacePy/edgy.png',edges)