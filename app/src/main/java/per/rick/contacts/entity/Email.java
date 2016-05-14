package per.rick.contacts.entity;

import java.util.List;

/**
 * 电子邮箱类
 * Created by Rick on 2016/5/11.
 */
public class Email extends BaseEntity {
	private int id;// 邮箱id
	private int personID;// 用户id
	private Person person = null;// 用户对象
	private String tag = null;// 邮箱的标签
	private String address = null;// 邮箱地址

	public List<String> diyTags = null;// 自定义标签
	public int selectTag = 0;// 当前标签在字符串数组中的索引

	/* set and get methods BEGIN */
	public int getId() {
		return id;
	}

	public Email setId(int id) {
		this.id = id;
		return this;
	}

	public int getPersonID() {
		return personID;
	}

	public Email setPersonID(int personID) {
		this.personID = personID;
		return this;
	}

	public Person getPerson() {
		return person;
	}

	public Email setPerson(Person person) {
		this.person = person;
		return this;
	}

	public String getTag() {
		return tag;
	}

	public Email setTag(String tag) {
		this.tag = tag;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public Email setAddress(String address) {
		this.address = address;
		return this;
	}
	/* set and get methods END */
}

