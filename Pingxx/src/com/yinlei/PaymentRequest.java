package com.yinlei;

public class PaymentRequest {
	String channel; // ֧������
	int amount; // �۸� ��

	public PaymentRequest(String channel, int amount) {
		this.channel = channel;
		this.amount = amount;
	}
}
