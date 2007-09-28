/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.validation;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.JpaModelManager;
import org.eclipse.jpt.core.internal.JpaProject;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.wst.validation.internal.core.ValidationException;
import org.eclipse.wst.validation.internal.core.ValidatorLauncher;
import org.eclipse.wst.validation.internal.operations.IWorkbenchContext;
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
		IJpaProject jpaProject = jpaHelper.getJpaProject();
		
		if (! ((JpaProject) jpaProject).isFilled()) {
			try {
				JpaModelManager.instance().fillJpaProject(jpaProject.getProject());
			}
			catch (CoreException ce) {
				return new Status(IStatus.ERROR, JptCorePlugin.PLUGIN_ID, JptCoreMessages.ERROR_SYNCHRONIZING_CLASSES_COULD_NOT_VALIDATE, ce);
			}
			
			JpaHelper newJpaHelper = new JpaHelper();
			newJpaHelper.setProject(jpaHelper.getProject());
			newJpaHelper.setValidationFileURIs(jpaHelper.getValidationFileURIs());
			ValidatorLauncher.getLauncher().start(newJpaHelper, this, reporter);
			
			return OK_STATUS;
		}
		
		reporter.removeAllMessages(this);
		
//		for (Iterator stream = jpaProject.validationMessages(); stream.hasNext(); ) {
//			reporter.addMessage(this, (Message) stream.next());
//		}
		
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
