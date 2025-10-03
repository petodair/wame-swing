package br.com.wame.model.dto;

public record LoginRequest(
		String username,
		String password) {}
