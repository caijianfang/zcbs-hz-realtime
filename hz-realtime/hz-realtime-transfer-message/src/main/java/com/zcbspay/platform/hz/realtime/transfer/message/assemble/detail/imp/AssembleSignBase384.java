package com.zcbspay.platform.hz.realtime.transfer.message.assemble.detail.imp;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zcbspay.platform.hz.realtime.common.utils.secret.RSAUtils;
import com.zcbspay.platform.hz.realtime.message.bean.CMT384Bean;
import com.zcbspay.platform.hz.realtime.transfer.message.api.bean.MessageBean;
import com.zcbspay.platform.hz.realtime.transfer.message.api.enums.ErrorCodeTransHZ;
import com.zcbspay.platform.hz.realtime.transfer.message.api.exception.HZRealTransferException;
import com.zcbspay.platform.hz.realtime.transfer.message.assemble.detail.AssembleSignBase;

@Service("assembleSignBase384")
public class AssembleSignBase384 implements AssembleSignBase {

    Logger logger = LoggerFactory.getLogger(AssembleSignBase384.class);

    @Override
    public byte[] signatureElement(MessageBean bean, String priKey) throws HZRealTransferException {
        byte[] signature = null;
        CMT384Bean msgBodyBean = (CMT384Bean) bean.getMessageBean();
        String msgBody = JSONObject.toJSONString(msgBodyBean);
        try {
            signature = RSAUtils.signBytes(msgBody.getBytes("utf-8"), priKey);
        }
        catch (UnsupportedEncodingException e) {
            logger.error("msgbody sign failed:UnsupportedEncodingException", e);
            throw new HZRealTransferException(ErrorCodeTransHZ.SIGN_FAILED);
        }
        catch (Exception e) {
            logger.error("msgbody sign failed:Exception", e);
            throw new HZRealTransferException(ErrorCodeTransHZ.SIGN_FAILED);
        }
        return signature;
    }

}
