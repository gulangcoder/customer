package cn.fintecher.app.model.bank;

import java.io.Serializable;

/**
 * @Auther: liangdeng
 * @Date: 2018/9/29
 * @Description:
 */
public class BankInfo implements Serializable {
    private static final long serialVersionUID = -176690774386840490L;
    private String bank;
    private String stat;
    private String validated;
    private String cardType;
    private String messages;
    private String key;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getValidated() {
        return validated;
    }

    public void setValidated(String validated) {
        this.validated = validated;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
