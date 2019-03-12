//package ru.nsu.fit.g16201.boldyrev.view.panels;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.ByteBuffer;
//
public class BitMap {
//    private BitMapFileHeader bmFH;
//    private BitMapInfoHeader bmIH;
//    private BitMapArray bmArray;
//
//    public class BitMapFileHeader {
//        private short type; //Сигнатура BM
//        private int size; //Размер файла
//        private short reserved1; //зарезервировано, равно 0
//        private short reserved2; //зарезервировано, равно 0
//        private int offsetBits; //смещение изображения от начала файла
//
//        public BitMapFileHeader(short type, int size, short reserved1, short reserved2, int offsetBits) {
//            this.type = type;
//            this.size = size;
//            this.reserved1 = reserved1;
//            this.reserved2 = reserved2;
//            this.offsetBits = offsetBits;
//        }
//
//        public BitMapFileHeader(BitMapFileHeader fileHeader) {
//            this.type = fileHeader.type;
//            this.size = fileHeader.size;
//            this.reserved1 = fileHeader.reserved1;
//            this.reserved2 = fileHeader.reserved2;
//            this.offsetBits = fileHeader.offsetBits;
//        }
//
//        public void readBMFH(InputStream input) throws Exception {
//            byte[] i = new byte[4];
//            byte[] s = new byte[2];
//
//            if(-1 == input.read(s)) {
//                throw new Exception("in.read() = -1");
//            }
//            type = bytesToShort(invertBytes(s));
//
//            if(-1 == input.read(i)) {
//                throw new Exception("in.read() = -1");
//            }
//            size = bytesToInt(invertBytes(i));
//
//            if(-1 == input.read(s)) {
//                throw new Exception("in.read() = -1");
//            }
//            reserved1 = bytesToShort(invertBytes(s));
//
//            if(-1 == input.read(s)) {
//                throw new Exception("in.read() = -1");
//            }
//            reserved2 = bytesToShort(invertBytes(s));
//
//            if(-1 == input.read(i)) {
//                throw new Exception("in.read() = -1");
//            }
//            offsetBits = bytesToInt(invertBytes(i));
//        }
//
//        public void writeBMFH*
//    }
//
//    private static byte[] invertBytes(byte[] b) {
//        int length = b.length;
//
//        for (int i = 0; i < length / 2; i++) {
//            byte buffer = b[length - 1 - i];
//            b[length - 1 - i] = b[i];
//            b[i] = buffer;
//        }
//        return b;
//    }
//
//    private static short bytesToShort(byte[] bytes) {
//        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
//
//        return byteBuffer.getShort();
//    }
//
//    private static int bytesToInt(byte[] bytes) {
//        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
//
//        return byteBuffer.getInt();
//    }
//
//    private static byte[] shortToBytes(short value) {
//        ByteBuffer buffer = ByteBuffer.allocate(2);
//        buffer.putShort(value);
//        buffer.flip();
//
//        return buffer.array();
//    }
//
//    private static byte[] intToBytes(int value) {
//        ByteBuffer buffer = ByteBuffer.allocate(4);
//        buffer.putInt(value);
//        buffer.flip();
//
//        return buffer.array();
//    }
}
