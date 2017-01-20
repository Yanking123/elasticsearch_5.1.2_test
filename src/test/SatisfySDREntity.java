package test;


import java.io.Serializable;

/**
 * @Description: 满意度sdr实体
 * @author xubing 
 * @date 2015年12月28日 下午6:51:39
 */
public class SatisfySDREntity implements Serializable{

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
     * 访客id
     */
    private String channelNo;
    /**
     * 访客id
     */
    private String workgroupId;
    
    /**
	 * 评价分数
	 */
    private int ssSatisfyScore;
    
    
    
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
	 * @return 返回 workgroupId。
	 */
	public String getWorkgroupId() {
		return workgroupId;
	}



	/**
	 * @param workgroupId 设置 workgroupId。
	 */
	public void setWorkgroupId(String workgroupId) {
		this.workgroupId = workgroupId;
	}



	@Override
	public String toString() {
		return "SatisfySDREntity [ssTime=" + ssTime + ", ssEnterpriseId=" + ssEnterpriseId + ", ssSessionId="
				+ ssSessionId + ", ssAgentId=" + ssAgentId +", ssSatisfyScore=" + ssSatisfyScore+",ssAppName="+ssAppName+"]";
	}
	

}
