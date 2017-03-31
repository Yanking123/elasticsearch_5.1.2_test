package test;

import java.io.Serializable;

/**
 * @Description: 满意度sdr实体
 * @author xubing
 * @date 2015年12月28日 下午6:51:39
 */
public class SatisfySDREntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 时间
     */
    private String ssTime;
    /**
     * 企业id
     */
    private String ssEnterpriseId;
    /**
     * 应用名称
     */
    private String ssAppName;
    /**
     * 会话id
     */
    private String ssSessionId;
    /**
     * 坐席id
     */
    private String ssAgentId;

    /**
     * 访客id
     */
    private String ssVisitorId;

    /**
     * 评价分数
     */
    private int ssSatisfyScore;
    /**
     * 渠道号
     */
    private String channelNo;
    /**
     * 技能组ID
     */
    private String workGroupId;

    /**
     * 渠道类型
     */
    private int ssChannelType;

    /**
     * 访客终端类型
     */
    private int ssTerminalType;

    /**
     * 访客地区
     */
    private int ssVisitoArea;



    public String getSsTime() {
        return ssTime;
    }



    public void setSsTime(String ssTime) {
        this.ssTime = ssTime;
    }



    public String getSsEnterpriseId() {
        return ssEnterpriseId;
    }



    public void setSsEnterpriseId(String ssEnterpriseId) {
        this.ssEnterpriseId = ssEnterpriseId;
    }



    public String getSsSessionId() {
        return ssSessionId;
    }



    public void setSsSessionId(String ssSessionId) {
        this.ssSessionId = ssSessionId;
    }



    public String getSsAgentId() {
        return ssAgentId;
    }



    public void setSsAgentId(String ssAgentId) {
        this.ssAgentId = ssAgentId;
    }



    public int getSsSatisfyScore() {
        return ssSatisfyScore;
    }



    public void setSsSatisfyScore(int ssSatisfyScore) {
        this.ssSatisfyScore = ssSatisfyScore;
    }



    public String getSsAppName() {
        return ssAppName;
    }


    public void setSsAppName(String ssAppName) {
        this.ssAppName = ssAppName;
    }

    public String getSsVisitorId() {
        return ssVisitorId;
    }

    public void setSsVisitorId(String ssVisitorId) {
        this.ssVisitorId = ssVisitorId;
    }



    /**
     * @return 返回 channelNo。
     */
    public String getChannelNo() {
        return channelNo;
    }



    /**
     * @param channelNo 设置 channelNo。
     */
    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }



    /**
     * @return 返回 workGroupId。
     */
    public String getWorkGroupId() {
        return workGroupId;
    }



    /**
     * @param workGroupId 设置 workGroupId。
     */
    public void setWorkGroupId(String workGroupId) {
        this.workGroupId = workGroupId;
    }



    /**
     * @return 返回 ssChannelType。
     */
    public int getSsChannelType() {
        return ssChannelType;
    }



    /**
     * @param ssChannelType 设置 ssChannelType。
     */
    public void setSsChannelType(int ssChannelType) {
        this.ssChannelType = ssChannelType;
    }



    /**
     * @return 返回 ssTerminalType。
     */
    public int getSsTerminalType() {
        return ssTerminalType;
    }



    /**
     * @param ssTerminalType 设置 ssTerminalType。
     */
    public void setSsTerminalType(int ssTerminalType) {
        this.ssTerminalType = ssTerminalType;
    }



    /**
     * @return 返回 ssVisitoArea。
     */
    public int getSsVisitoArea() {
        return ssVisitoArea;
    }



    /**
     * @param ssVisitoArea 设置 ssVisitoArea。
     */
    public void setSsVisitoArea(int ssVisitoArea) {
        this.ssVisitoArea = ssVisitoArea;
    }

    @Override
    public String toString() {
        return "SatisfySDREntity [ssTime=" + ssTime + ", ssEnterpriseId=" + ssEnterpriseId
                + ", ssAppName=" + ssAppName + ", ssSessionId=" + ssSessionId + ", ssAgentId="
                + ssAgentId + ", ssVisitorId=" + ssVisitorId + ", ssSatisfyScore=" + ssSatisfyScore
                + ", channelNo=" + channelNo + ", workGroupId=" + workGroupId + ", ssChannelType="
                + ssChannelType + ", ssTerminalType=" + ssTerminalType + ", ssVisitoArea="
                + ssVisitoArea + "]";
    }


}
