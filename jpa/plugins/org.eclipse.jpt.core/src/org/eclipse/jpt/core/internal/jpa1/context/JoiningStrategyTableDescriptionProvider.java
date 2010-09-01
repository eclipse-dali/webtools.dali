package org.eclipse.jpt.core.internal.jpa1.context;

import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.internal.jpa1.context.BaseColumnTableValidator.TableDescriptionProvider;

public class JoiningStrategyTableDescriptionProvider implements TableDescriptionProvider
{
	
	private final JoiningStrategy joiningStrategy;
	
	public JoiningStrategyTableDescriptionProvider(JoiningStrategy joiningStrategy) {
		super();
		this.joiningStrategy = joiningStrategy;
	}

	public String getColumnTableDescriptionMessage() {
		return this.joiningStrategy.getColumnTableNotValidDescription();
	}

}
