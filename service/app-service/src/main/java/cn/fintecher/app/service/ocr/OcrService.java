package cn.fintecher.app.service.ocr;

import java.util.Map;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年06月16日 11:00<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:吴城 <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */

public interface OcrService {

    String getNonceTicket(Map ticketMap) throws Exception;

    boolean validation(Map map) throws Exception;

    String getAccessToken(String companyCode)throws Exception;

    Map saveCustomerFace(Map pmap)throws Exception;

    String getTencentOcrSignTicket(Map ticketMap) throws Exception;

}
