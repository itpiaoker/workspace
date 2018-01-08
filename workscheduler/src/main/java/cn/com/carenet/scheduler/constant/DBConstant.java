package cn.com.carenet.scheduler.constant;

public class DBConstant {
	/** 0.保存失败 */
	public static final Integer TASK_INFO_EXEC_STATE_SAVE_FAILED = 0;
	/** 1.保存成功 */
	public static final Integer TASK_INFO_EXEC_STATE_SAVE_SUCCESS = 1;
	/** 2.保存中 */
	public static final Integer TASK_INFO_EXEC_STATE_SAVING = 2;
	/** 3.执行失败 */
	public static final Integer TASK_INFO_EXEC_STATE_EXEC_FAILED = 3;
	/** 4.执行中 */
	public static final Integer TASK_INFO_EXEC_STATE_EXECING = 4;
	/** 5执行成功 */
	public static final Integer TASK_INFO_EXEC_STATE_EXEC_SUCCESS = 5;

	/** 0:未删除 */
	public static final Integer TASK_INFO_DELETE_FLAG_NOT_DELETED = 0;
	/** 1:已删除 */
	public static final Integer TASK_INFO_DELETE_FLAG_DELETED = 1;

	/** 0:任务未挂起 */
	public static final Integer TASK_INFO_HANG_STATUS_WAIT = 0;
	/** 1:任务挂起 */
	public static final Integer TASK_INFO_HANG_STATUS_RUNNING = 1;
}
