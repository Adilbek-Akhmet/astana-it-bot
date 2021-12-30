package soft.astanaitbot.telegramBotService.model;

public enum BotState {
    PICK_LANGUAGE,

    AUTHORIZATION,
    WRITE_EMAIL,
    CONFIRM_EMAIL,
    AUTHORIZED,

    COMPLAINT,
    COMPLAINTtoSTUDENT,
    COMPLAINTtoTEACHER,
    COMPLAINTtoADMINISTRATION,

    FINISH
}
