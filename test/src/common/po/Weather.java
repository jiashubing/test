package common.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 天气预报订阅
 * @author JiaShubing
 *
 */
@Entity
@Table(name="db_weather")
public class Weather implements Serializable{

	private static final long serialVersionUID = 3512947258567372253L;

	private long id;
	private String province;	//省份
	private String city;		//城市
	private String cityCode;	//城市代码
	private String cityPicture;	//城市图片名称
	private String lastUpdateDate;	//最后更新时间
	private String todayTemperature;	//当天气温
	private String todaySituation;	//当天概况
	private String todayWind;	//当天风向和风力
	private String todayIconOne;	//当天图标一
	private String todayIconTwo;	//当天图标一
	private String todayActual;	//当天天气实况
	private String todayLife;	//当天生活指数
	private String secondTemperature;	//第二天的气温
	private String secondSituation;	//第二天的概况
	private String secondWind;	//第二天的风向和风力
	private String secondIconOne;	//第二天的图标一
	private String secondIconTwo;	//第二天的图标一
	private String thirdTemperature;	//第三天的气温
	private String thirdSituation;	//第三天的概况
	private String thirdWind;	//第三天的风向和风力
	private String thirdIconOne;	//第三天的图标一
	private String thirdIconTwo;	//第三天的图标一
	private String cityIntroduce;	//被查询的城市或地区的介绍 	
	
	public Weather(){}
	
	public Weather(String province,
			String city,
			String cityCode,
			String cityPicture,
			String lastUpdateDate,
			String todayTemperature,
			String todaySituation,
			String todayWind,
			String todayIconOne,
			String todayIconTwo,
			String todayActual,
			String todayLife,
			String secondTemperature,
			String secondSituation,
			String secondWind,
			String secondIconOne,
			String secondIconTwo,
			String thirdTemperature,
			String thirdSituation,
			String thirdWind,
			String thirdIconOne,
			String thirdIconTwo,
			String cityIntroduce){
		this.province = province;
		this.city = city;
		this.cityCode =cityCode;
		this.cityPicture = cityPicture;
		this.lastUpdateDate = lastUpdateDate;
		this.todayTemperature = todayTemperature;
		this.todaySituation = todaySituation;
		this.todayWind = todayWind;
		this.todayIconOne = todayIconOne;
		this.todayIconTwo = todayIconTwo;
		this.todayActual = todayActual;
		this.todayLife = todayLife;
		this.secondTemperature = secondTemperature;
		this.secondSituation = secondSituation;
		this.secondWind = secondWind;
		this.secondIconOne = secondIconOne;
		this.secondIconTwo = secondIconTwo;
		this.thirdTemperature = thirdTemperature;
		this.thirdSituation = thirdSituation;
		this.thirdWind = thirdWind;
		this.thirdIconOne = thirdIconOne;
		this.thirdIconTwo = thirdIconTwo;
		this.cityIntroduce = cityIntroduce;
	}

	@Id
	@GeneratedValue(generator="_native")
	@GenericGenerator(name="_native",strategy="native")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(length=10)
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(length=20)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(length=10)
	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Column(length=15)
	public String getCityPicture() {
		return cityPicture;
	}

	public void setCityPicture(String cityPicture) {
		this.cityPicture = cityPicture;
	}

	@Column(length=20)
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(length=15)
	public String getTodayTemperature() {
		return todayTemperature;
	}

	public void setTodayTemperature(String todayTemperature) {
		this.todayTemperature = todayTemperature;
	}

	@Column(length=100)
	public String getTodaySituation() {
		return todaySituation;
	}

	public void setTodaySituation(String todaySituation) {
		this.todaySituation = todaySituation;
	}

	@Column(length=50)
	public String getTodayWind() {
		return todayWind;
	}

	public void setTodayWind(String todayWind) {
		this.todayWind = todayWind;
	}

	@Column(length=10)
	public String getTodayIconOne() {
		return todayIconOne;
	}

	public void setTodayIconOne(String todayIconOne) {
		this.todayIconOne = todayIconOne;
	}

	@Column(length=10)
	public String getTodayIconTwo() {
		return todayIconTwo;
	}

	public void setTodayIconTwo(String todayIconTwo) {
		this.todayIconTwo = todayIconTwo;
	}

	@Column
	public String getTodayActual() {
		return todayActual;
	}

	public void setTodayActual(String todayActual) {
		this.todayActual = todayActual;
	}

	@Column
	public String getTodayLife() {
		return todayLife;
	}

	public void setTodayLife(String todayLife) {
		this.todayLife = todayLife;
	}

	@Column(length=15)
	public String getSecondTemperature() {
		return secondTemperature;
	}

	public void setSecondTemperature(String secondTemperature) {
		this.secondTemperature = secondTemperature;
	}

	@Column(length=20)
	public String getSecondSituation() {
		return secondSituation;
	}

	public void setSecondSituation(String secondSituation) {
		this.secondSituation = secondSituation;
	}

	@Column(length=50)
	public String getSecondWind() {
		return secondWind;
	}

	public void setSecondWind(String secondWind) {
		this.secondWind = secondWind;
	}

	@Column(length=10)
	public String getSecondIconOne() {
		return secondIconOne;
	}

	public void setSecondIconOne(String secondIconOne) {
		this.secondIconOne = secondIconOne;
	}

	@Column(length=10)
	public String getSecondIconTwo() {
		return secondIconTwo;
	}

	public void setSecondIconTwo(String secondIconTwo) {
		this.secondIconTwo = secondIconTwo;
	}

	@Column(length=20)
	public String getThirdTemperature() {
		return thirdTemperature;
	}

	public void setThirdTemperature(String thirdTemperature) {
		this.thirdTemperature = thirdTemperature;
	}

	@Column(length=20)
	public String getThirdSituation() {
		return thirdSituation;
	}

	public void setThirdSituation(String thirdSituation) {
		this.thirdSituation = thirdSituation;
	}

	@Column(length=20)
	public String getThirdWind() {
		return thirdWind;
	}

	public void setThirdWind(String thirdWind) {
		this.thirdWind = thirdWind;
	}

	@Column(length=10)
	public String getThirdIconOne() {
		return thirdIconOne;
	}

	public void setThirdIconOne(String thirdIconOne) {
		this.thirdIconOne = thirdIconOne;
	}

	@Column(length=10)
	public String getThirdIconTwo() {
		return thirdIconTwo;
	}

	public void setThirdIconTwo(String thirdIconTwo) {
		this.thirdIconTwo = thirdIconTwo;
	}

	@Column(length=1000)
	public String getCityIntroduce() {
		return cityIntroduce;
	}

	public void setCityIntroduce(String cityIntroduce) {
		this.cityIntroduce = cityIntroduce;
	}

}
