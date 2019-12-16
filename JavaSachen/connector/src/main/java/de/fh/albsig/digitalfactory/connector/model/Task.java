package de.fh.albsig.digitalfactory.connector.model;

import java.util.Objects;


public final class Task
{

	private String transactionKey;
	private String description;
	private String operation;

	private double targetTemperature;
	private long duration;

	private boolean stirrerEnabled;
	private boolean heaterEnabled;


	public Task() {}

	public Task(final String transactionKey, final String description,
				final double targetTemperature, final long duration,
				final boolean stirrerEnabled, final boolean heaterEnabled,
				final String operation)
	{
		this.transactionKey = transactionKey;
		this.description = description;
		this.operation = operation;
		this.targetTemperature = targetTemperature;
		this.duration = duration;
		this.stirrerEnabled = stirrerEnabled;
		this.heaterEnabled = heaterEnabled;
	}


	@Override
	public String toString()
	{
		return "Task [transactionKey=" + this.transactionKey
				+ ", operation=" + this.operation
				+ ", description=" + this.description
				+ ", targetTemperature="+ this.targetTemperature
				+ ", duration=" + this.duration
				+ ", stirrerEnabled=" + this.stirrerEnabled
				+ ", heaterEnabled=" + this.heaterEnabled + "]";
	}

	@Override
	public boolean equals(final Object object)
	{
		if(object == null) return false;
		if(this == object) return true;

		if(this.getClass() != object.getClass())
			return false;

		final Task task = (Task) object;
		return Objects.equals(this.transactionKey, task.getTransactionKey())
			&& Objects.equals(this.operation, task.getOperation())
			&& Objects.equals(this.description, task.getDescription())
			&& Objects.equals(this.targetTemperature, task.getTargetTemperature())
			&& Objects.equals(this.duration, task.getDuration())
			&& Objects.equals(this.stirrerEnabled, task.isStirrerEnabled())
			&& Objects.equals(this.heaterEnabled, task.isHeaterEnabled());
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.transactionKey, this.description,
							this.targetTemperature, this.duration,
							this.stirrerEnabled, this.heaterEnabled);
	}


	public String getTransactionKey()
	{
		return this.transactionKey;
	}

	public void setTransactionKey(final String transactionKey)
	{
		this.transactionKey = transactionKey;
	}

	public String getOperation()
	{
		return this.operation;
	}

	public void setOperation(final String operation)
	{
		this.operation = operation;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}

	public double getTargetTemperature()
	{
		return this.targetTemperature;
	}

	public void setTargetTemperature(final double targetTemperature)
	{
		this.targetTemperature = targetTemperature;
	}

	public long getDuration()
	{
		return this.duration;
	}

	public void setDuration(final long duration)
	{
		this.duration = duration;
	}

	public boolean isStirrerEnabled()
	{
		return this.stirrerEnabled;
	}

	public void setStirrerEnabled(final boolean stirrerEnabled)
	{
		this.stirrerEnabled = stirrerEnabled;
	}

	public boolean isHeaterEnabled()
	{
		return this.heaterEnabled;
	}

	public void setHeaterEnabled(final boolean heaterEnabled)
	{
		this.heaterEnabled = heaterEnabled;
	}

}
