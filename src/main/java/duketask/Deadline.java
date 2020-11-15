package duketask;

import dukeexception.DukeException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Todo {
    private boolean isFormatted;
    private LocalDateTime byDateTime;
    private String formattedDateTime;
    private DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
    private DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mma");

    /**
     * Constructor of <code>Deadline</code> class, initialize Deadline task description and schedule.
     *
     * @param taskData the String received as description of the task
     */
    public Deadline(String taskData) {
        super(taskData);

        assert !schedule.isEmpty() : "Schedule is not provided";

        if (taskData.contains("/by")) {
            formatDateTime();
        }
    }

    /**
     * Format Deadline <code>schedule</code> to the "dd MMM yyyy, hh:mma" format.
     * If cannot be formatted, the schedule would stay the same.
     */
    private void formatDateTime() {
        try {
            byDateTime = LocalDateTime.parse(schedule, inputFormat);
            formattedDateTime = byDateTime.format(outputFormat);
            isFormatted = true;
        }catch (Exception e){
            isFormatted = false;
        }
    }

    /**
     * Return Deadline task <code>schedule</code>.
     *
     * @return schedule String of the Deadline task, or the formatted datetime as a String
     */
    @Override
    protected String getSchedule() {
        if(isFormatted){
            return formattedDateTime;
        }
        return schedule;
    }

    /**
     * Change the task <code>schedule</code>.
     *
     * @param schedule A String of the new schedule
     */
    @Override
    public void setSchedule(String schedule) {
        this.schedule = schedule;
        if(!isDuration) {
            formatDateTime();
        }
    }

    /**
     * Change both description and schedule of the Deadline task.
     *
     * @param input a String of the new task information
     */
    @Override
    public void reset(String input) {
        buffer = input.split("\\/", 2);
        if (input.contains("/takes")) {
            isDuration = true;
        }
        description = buffer[0].trim();
        schedule = buffer[1].split("\\s", 2)[1].trim();
        isDuration = false;
        formatDateTime();
    }

    /**
     * Copy the Event task <code>information</code>.
     *
     * @return the String of the Event task information
     */
    public String copy() {
        if(isDuration = true){
            return description + " /takes " + schedule;
        }
        return description + " /by " + schedule;
    }

    /**
     * Convert and return the Deadline task as a String.
     *
     * @return A String of the Deadline task
     */
    @Override
    public String toString() {
        if (isDuration) {
            return String.format("[D][%s] %s (takes: %s)", getStatusIcon(), getDescription(), getSchedule());
        }
        return String.format("[D][%s] %s (by: %s)", getStatusIcon(), getDescription(), getSchedule());
    }
}
