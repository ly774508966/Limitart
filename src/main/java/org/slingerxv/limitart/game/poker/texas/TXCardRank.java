package org.slingerxv.limitart.game.poker.texas;

/*
 * 货币类型
 */
public enum TXCardRank {
	/**
	 * 高牌
	 */
	HIGH_CARD(1),
	/**
	 * 一对
	 */
	ONE_PAIR(2),
	/**
	 * 两对
	 */
	TWO_PAIR(3),
	/**
	 * 三条
	 */
	THREE_OF_A_KIND(4),
	/**
	 * 顺子
	 */
	STRAIGHT(5),
	/**
	 * 同花
	 */
	FLUSH(6),
	/**
	 * 葫芦
	 */
	FULL_HOUSE(7),
	/**
	 * 四条
	 */
	FOUR_OF_A_KIND(8),
	/**
	 * 同花顺
	 */
	STRAIGHT_FLUSH(9),
	/**
	 * 皇家同花顺
	 */
	ROYAL_FLUSH(10),;
	private int value;

	TXCardRank(int value) {
		this.value = value;
	}

	public long getValue() {
		return this.value;
	}
	
	public static TXCardRank getTXCardRank(long num){
		for(TXCardRank txCardRank : TXCardRank.values()){
			if(num == txCardRank.getValue()){
				return txCardRank;
			}
		}
		return null;
	}

}
