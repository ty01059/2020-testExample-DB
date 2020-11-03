package com.sbs.test.testGame;

import java.util.Calendar;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int num = sc.nextInt();
		캐릭터[] 캐릭터들 = new 캐릭터[num];

		String 이름 = sc.next();
		String 생일 = sc.next();

		String[] 이름들 = 이름.split("/");
		String[] 태어난해 = 생일.split("년");

		for (int i = 0; i < num; i++) {
			캐릭터 a캐릭터 = null;

			String 직업괄호 = sc.next();
			String 직업 = 직업괄호.replace("[", "").replace("]", "");

			if (직업.equals("의적")) {
				a캐릭터 = new 의적();
			} else if (직업.equals("도적")) {
				a캐릭터 = new 도적();
			} else if (직업.equals("상인")) {
				a캐릭터 = new 상인();
			}

			a캐릭터.번호 = i + 1;
			a캐릭터.이름 = 이름들[i];
			a캐릭터.태어난해 = Integer.parseInt(태어난해[i]);

			캐릭터들[i] = a캐릭터;
		}

		for (int i = 0; i < num; i++) {
			String 능력치들 = sc.next();
			String[] 능력치 = 능력치들.split(",");
			int 힘 = Integer.parseInt(능력치[0]);
			int 지능 = Integer.parseInt(능력치[1]);
			int 민첩 = Integer.parseInt(능력치[2]);

			캐릭터들[i].힘 = 힘;
			캐릭터들[i].지능 = 지능;
			캐릭터들[i].민첩 = 민첩;
		}

		sc.close();

		for (int i = 0; i < num; i++) {
			캐릭터들[i].자기소개();
			캐릭터들[i].공격();
		}
	}
}

class 캐릭터 {
	int 번호;
	String 이름;
	int 태어난해;
	String 직업;
	int 힘;
	int 지능;
	int 민첩;

	무기 a무기;

	int get나이() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		return year - 태어난해;
	}

	void 자기소개() {
		System.out.println("== 자기소개 시작 ==");
		System.out.printf("번호 : %d번\n", 번호);
		System.out.printf("이름 : %s\n", 이름);
		System.out.printf("캐릭터 종류 : %s\n", 직업);
		System.out.printf("태어난 해 : %04d년\n", 태어난해);
		System.out.printf("나이 : %d살\n", get나이());
		System.out.printf("힘 : %d\n", 힘);
		System.out.printf("지능 : %d\n", 지능);
		System.out.printf("민첩 : %d\n", 민첩);
		System.out.println("== 자기소개 끝 ==");
	}

	void 공격() {
		System.out.println("== 공격 시작 ==");
		a무기.작동(이름, 직업, 힘, 민첩);
		System.out.println("== 공격 끝 ==");
	}
}

class 의적 extends 캐릭터 {
	의적() {
		a무기 = new 검();
		직업 = "의적";
	}
}

class 도적 extends 캐릭터 {
	도적() {
		a무기 = new 도끼();
		직업 = "도적";
	}
}

class 상인 extends 캐릭터 {
	상인() {
		a무기 = new 지팡이();
		직업 = "상인";
	}
}

class 무기 {
	String 이름;
	int 공격력;

	void 작동(String 사용자_이름, String 사용자_직업, int 사용자_힘, int 사용자_민첩) {
		System.out.printf("- 직업이 %s인 %s(이)가 %s(으)로 공격합니다.\n", 사용자_직업, 사용자_이름, 이름);
		System.out.printf("- 무기공격력 : %d\n", 공격력);
		System.out.printf("- 데미지 : %d\n", 공격력 * 사용자_힘 * 사용자_민첩);
	}
}

class 검 extends 무기 {
	검() {
		이름 = "검";
		공격력 = 10;
	}
}

class 도끼 extends 무기 {
	도끼() {
		이름 = "도끼";
		공격력 = 15;
	}
}

class 지팡이 extends 무기 {
	지팡이() {
		이름 = "지팡이";
		공격력 = 2;
	}
}