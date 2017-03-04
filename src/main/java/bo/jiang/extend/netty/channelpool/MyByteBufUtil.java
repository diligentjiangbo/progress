package bo.jiang.extend.netty.channelpool;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 帮助ByteBuf进行转换的类
 */
public class MyByteBufUtil {

  /**
   * 将ByteBuf中的内容转换为String
   * @param byteBuf
   * @return
   */
  public static String byteBuf2String(ByteBuf byteBuf) {
    byte[] bytes = new byte[byteBuf.readableBytes()];
    byteBuf.readBytes(bytes);
    return new String(bytes);
  }

  /**
   * 将String转换为ByteBuf对象
   * @param str
   * @return
   */
  public static ByteBuf string2ByteBuf(String str) {
    return Unpooled.wrappedBuffer(str.getBytes());
  }

}
