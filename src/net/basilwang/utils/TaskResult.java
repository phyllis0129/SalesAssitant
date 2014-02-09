package net.basilwang.utils;

public enum TaskResult {
	OK, FAILED, CANCELLED,

	NOT_FOLLOWED_ERROR, IO_ERROR, AUTH_ERROR,

	AUTH_ERROR_NOT_MEMBER, GET_CODE_OK
}