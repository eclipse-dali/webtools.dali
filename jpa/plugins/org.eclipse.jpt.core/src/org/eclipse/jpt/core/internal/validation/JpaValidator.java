/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.validation;

import java.util.Iterator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.wst.validation.internal.core.ValidationException;
import org.eclipse.wst.validation.internal.operations.IWorkbenchContext;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.wst.validation.internal.provisional.core.IValidationContext;
import org.eclipse.wst.validation.internal.provisional.core.IValidatorJob;

public class JpaValidator implements IValidatorJob
{
	public ISchedulingRule getSchedulingRule(IValidationContext helper) {
		// don't know what to return here.  my guess is that we want to return
		// the resource that is possibly being changed during our validation,
		// and since many resources in the project may be changed during this
		// validation, returning the project makes the most sense.
		return ((IWorkbenchContext) helper).getProject();
	}

	public IStatus validateInJob(IValidationContext helper, IReporter reporter) throws ValidationException {
		JpaHelper jpaHelper = (JpaHelper) helper;
		
		reporter.removeAllMessages(this);
		
		for (Iterator<IMessage> stream = jpaHelper.getJpaProject().validationMessages(); stream.hasNext(); ) {
			reporter.addMessage(this, stream.next());
		}
		
		return OK_STATUS;
	}

	public void cleanup(IReporter reporter) {
	// TODO Auto-generated method stub
		return;
	}

	public void validate(IValidationContext helper, IReporter reporter) throws ValidationException {
		validateInJob(helper, reporter);
	}
}
