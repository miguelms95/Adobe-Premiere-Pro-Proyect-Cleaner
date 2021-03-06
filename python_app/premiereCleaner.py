# -*- coding: utf-8 -*-
import os
import fnmatch

TRACEBACKPRINT = True
DELETING = True
PATH = 'D:\\test\\'

def main():
    print ' *** Adobe Premiere Pro Cleaner ***' \
          '\nClean the Premiere Projects before backuping or moving it anywhere deleting unnecessary preview files, and audio / video renders for real time editing: .CFA, .PEK, .AVI.' \
          '' \
          '\nInput example: D:\\test\\' 

    input_path = raw_input("Introduce path to scan: ")

    PATH = input_path

    contadorPEK = 0
    contadorCFA = 0
    contadorAVI = 0

    PEKsize = 0
    CFAsize = 0
    AVIsize = 0

    for root, dirs, files in os.walk(PATH):
        for directory in dirs:
            if directory.endswith(".PRV"):
                list = os.listdir(os.path.join(root, directory))
                countfiles = len(list)
                print directory + ', files: ' + str(countfiles)
        for file in files:

            if file.endswith(".pek"):
                statinfo = os.stat(os.path.join(root, file))
                contadorPEK += 1
                PEKsize += statinfo.st_size
                if TRACEBACKPRINT:
                    print '\t' + file + ' - ' + str(statinfo.st_size) + ' Bytes'
                if DELETING:
                    os.remove(os.path.join(root, file))

            if file.endswith(".cfa"):
                statinfo = os.stat(os.path.join(root, file))
                contadorCFA += 1
                CFAsize += statinfo.st_size
                if TRACEBACKPRINT:
                    print '\t' + file + ' - ' + str(statinfo.st_size) + ' Bytes'
                if DELETING:
                    os.remove(os.path.join(root, file))
            if fnmatch.fnmatch(file, 'Rendered - *.AVI'):
                statinfo = os.stat(os.path.join(root, file))
                contadorAVI += 1
                AVIsize += statinfo.st_size
                if TRACEBACKPRINT:
                    print '\t' + file + ' - ' + str(statinfo.st_size) + ' Bytes'
                if DELETING:
                    os.remove(os.path.join(root, file))
        for directory in dirs:
            if directory.endswith(".PRV"):
                list = os.listdir(os.path.join(root, directory))
                countfiles = len(list)
                if DELETING:
                    if (countfiles == 0):
                        os.rmdir(os.path.join(root, directory))

    print '\n\n#### DATOS eliminados ####'
    print str(contadorAVI) + " AVI files" + ' - ' + str(AVIsize) + ' Bytes = ' + str((AVIsize*1.0)/1000000000.0) + ' GB'
    print str(contadorPEK) + " PEK files" + ' - ' + str(PEKsize) + ' Bytes = ' + str((PEKsize*1.0)/1000000000.0) + ' GB'
    print str(contadorCFA) + " CFA files" + ' - ' + str(CFAsize) + ' Bytes = ' + str((CFAsize*1.0)/1000000000.0) + ' GB'
    totalSize = AVIsize + PEKsize + CFAsize
    print 'Space cleaned: ' + str(totalSize) + ' Bytes = ' + str((totalSize*1.0)/1000000000.0) + ' GB'

    #raw_input("\nPresiona una tecla para cerrar...")
    raw_input("\nPress any key to exit...")
    exit(0)

if __name__ == '__main__':
    main()
