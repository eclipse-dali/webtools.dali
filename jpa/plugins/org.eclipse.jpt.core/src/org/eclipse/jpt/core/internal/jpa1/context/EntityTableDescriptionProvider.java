package org.eclipse.jpt.core.internal.jpa1.context;

import org.eclipse.jpt.core.internal.jpa1.context.BaseColumnTableValidator.TableDescriptionProvider;
import org.eclipse.jpt.core.internal.validation.JpaValidationDescriptionMessages;

public class EntityTableDescriptionProvider implements TableDescriptionProvider
{
	public String getColumnTableDescriptionMessage() {
		return JpaValidationDescriptionMessages.NOT_VALID_FOR_THIS_ENTITY;
	}

}
