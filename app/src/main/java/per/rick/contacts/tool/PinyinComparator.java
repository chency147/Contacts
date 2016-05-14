package per.rick.contacts.tool;

import java.util.Comparator;

import per.rick.contacts.entity.Person;

/**
 * 拼音比较器
 * Created by Rick on 2016/5/11.
 */
public class PinyinComparator implements Comparator<Person> {
	@Override
	public int compare(Person lhs, Person rhs) {
		if ("@".equals(lhs.getSortKey())
				|| "#".equals(rhs.getSortKey())) {
			return -1;
		} else if ("#".equals(lhs.getSortKey())
				|| "@".equals(rhs.getSortKey())) {
			return 1;
		} else {
			return lhs.getSortKey().compareTo(rhs.getSortKey());
		}
	}
}
