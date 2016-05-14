package per.rick.contacts.entity;

import android.content.Context;

import java.util.List;

/**
 * Created by Rick on 2016/5/11.
 */
public class Phone extends BaseEntity {
	private int id;// 号码的id
	private int personID;// 用户id
	private Person person;// 用户对象
	private String tag = null;// 号码的标签
	private String number = null;// 号码的内容

	public List<String> diyTags = null;// 自定义标签
	public int selectTag = 0;// 当前标签在字符串数组中的索引

	/* set and get methods BEGIN */
	public int getId() {
		return id;
	}

	public Phone setId(int id) {
		this.id = id;
		return this;
	}

	public int getPersonID() {
		return personID;
	}

	public Phone setPersonID(int personID) {
		this.personID = personID;
		return this;
	}

	public Person getPerson() {
		return person;
	}

	public Phone setPerson(Person person) {
		this.person = person;
		return this;
	}

	public String getTag() {
		return tag;
	}

	public Phone setTag(String tag) {
		this.tag = tag;
		return this;
	}

	public String getNumber() {
		return number;
	}

	public Phone setNumber(String number) {
		this.number = number;
		return this;
	}
	/* set and get methods END */
}
