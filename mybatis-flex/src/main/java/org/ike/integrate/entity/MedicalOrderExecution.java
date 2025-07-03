package org.ike.integrate.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

@Data
@Table("medical_order_execution")
public class MedicalOrderExecution {

	@Id(keyType = KeyType.None)
	private long id;
	// 字段定义
	private String field1;
	private String field2;
	private String field3;
	private String field4;
	private String field5;
	private String field6;
	private String field7;
	private String field8;
	private String field9;
	private String field10;
	private String field11;
	private String field12;
	private String field13;
	private String field14;
	private String field15;
	private String field16;
	private String field17;
	private String field18;
	private String field19;
	private String field20;
	private String field21;
	private String field22;
	private String field23;
	private String field24;
	private String field25;
	private String field26;
	private String field27;
	private String field28;
	private String field29;
	private String field30;
	private String field31;
	private String field32;
	private String field33;
	private String field34;
	private String field35;
	private String field36;
	private String field37;
	private String field38;
	private String field39;
	private String field40;
	private String field41;
	private String field42;
	private String field43;
	private String field44;
	private String field45;
	private String field46;
	private String field47;
	private String field48;
	private String field49;
	private String field50;
	private String field51;
	private String field52;
	private String field53;
	private String field54;
	private String field55;
	private String field56;
	private String field57;
	private String field58;
	private String field59;
	private String field60;
	private String field61;
	private String field62;
	private String field63;
	private String field64;
	private String field65;
	private String field66;
	private String field67;
	private String field68;
	private String field69;
	private String field70;
	private String field71;
	private String field72;
	private String field73;
	private String field74;
	private String field75;
	private String field76;
	private String field77;
	private String field78;
	private String field79;
	private String field80;
	private String field81;
	private String field82;
	private String field83;
	private String field84;
	private String field85;
	private String field86;
	private String field87;
	private String field88;
	private String field89;
	private String field90;
	private String field91;
	private String field92;
	private String field93;
	private String field94;
	private String field95;
	private String field96;
	private String field97;
	private String field98;
	private String field99;
	private String field100;
	private String field101;
	private String field102;
	private String field103;
	private String field104;
	private String field105;
	private String field106;
	private String field107;
	private String field108;
	private String field109;
	private String field110;
	private String field111;
	private String field112;
	private String field113;
	private String field114;
	private String field115;
	private String field116;
	private String field117;
	private String field118;
	private String field119;
	private String field120;
	private String field121;
	private String field122;
	private String field123;
	private String field124;
	private String field125;
	private String field126;
	private String field127;
	private String field128;
	private String field129;
	private String field130;
	private String field131;
	private String field132;
	private String field133;
	private String field134;
	private String field135;
	private String field136;
	private String field137;
	private String field138;
	private String field139;
	private String field140;
	private String field141;
	private String field142;
	private String field143;
	private String field144;
	private String field145;
	private String field146;
	private String field147;
	private String field148;
	private String field149;
	private String field150;

	public String convertToCSV() {
		return Arrays.stream(this.getClass().getDeclaredFields())
//				.sorted(Comparator.comparing(Field::getName)) // 保证字段顺序一致性
				.map(field -> {
					try {
						field.setAccessible(true);
						Object value = field.get(this);

						// 空值处理
						if (value == null) return "";

						// 特殊类型格式化
						if (value instanceof LocalDateTime) {
							return ((LocalDateTime) value)
									.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
						}
						if (value instanceof Boolean) {
							return ((Boolean) value) ? "1" : "0";
						}

						String strValue = value.toString();

						// CSV特殊字符转义规则：包含逗号/双引号/换行符时添加双引号
						if (strValue.contains(",")
								|| strValue.contains("\"")
								|| strValue.contains("\n")) {
							strValue = "\"" + strValue.replace("\"", "\"\"") + "\"";
						}
						return strValue;
					} catch (IllegalAccessException e) {
						throw new RuntimeException("Reflection access error", e);
					}
				})
				.collect(Collectors.joining(","));
	}
}
