package br.com.wame.model.dto;

public record ApiResponse<D>(
	    String status,
	    int code,
	    D data,
	    String message) {
	
}
