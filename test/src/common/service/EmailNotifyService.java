package common.service;

import javax.mail.MessagingException;

import common.entity.EmailVo;

import java.util.Date;

/**
 * Email通知消息发送
 */
public interface EmailNotifyService
{
    /**
     * 发送简单文本Email消息
     * @param emailVo
     */
    public void sendEmailMessageOfSimpleText(EmailVo emailVo, Date date);

    /**
     * 发送HTML格式的消息
     * @param emailVo
     * @param date
     */
    public void sendEmailMessageOfHtmlText(EmailVo emailVo , Date date) throws MessagingException;

    /**
     * 带附件并且html格式邮件发送,带附件并简单文本格式邮件发送
     * @param emailVo
     */
    public void sendEmailMessageOfAttachedFileAndSimpleText(EmailVo emailVo, Date date, boolean isHtmlText) throws MessagingException;

}