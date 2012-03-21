/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.validation;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.prefs.JpaValidationPreferencesManager;
import org.eclipse.wst.validation.AbstractValidator;
import org.eclipse.wst.validation.ValidationResult;
import org.eclipse.wst.validation.ValidationState;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IProjectValidationContext;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.wst.validation.internal.provisional.core.IValidationContext;
import org.eclipse.wst.validation.internal.provisional.core.IValidator;

/**
 * This class is referenced in the JPA extension for the
 * WTP validator extension point.
 */
public class JpaValidator
		extends AbstractValidator
		implements IValidator {
	
	public JpaValidator() {
		super();
	}


	// ********** IValidator implementation **********

	public void validate(IValidationContext context, IReporter reporter) {
		this.validate(reporter, ((IProjectValidationContext) context).getProject());
	}

	public void cleanup(IReporter reporter) {
		// nothing to do
	}


	// ********** AbstractValidator implementation **********

	@Override
	public ValidationResult validate(IResource resource, int kind, ValidationState state, IProgressMonitor monitor) {
		if (resource.getType() != IResource.FILE) {
			return null;
		}
		ValidationResult result = new ValidationResult();
		IReporter reporter = result.getReporter(monitor);
		IProject project = resource.getProject();
		result.setSuspendValidation(project);
		this.validate(reporter, project);
		return result;
	}


	// ********** internal **********

	private void clearMarkers(IProject project) {
		try {
			this.clearMarkers_(project);
		} catch (CoreException ex) {
			JptJpaCorePlugin.log(ex);
		}
	}

	private void clearMarkers_(IProject project) throws CoreException {
		IMarker[] markers = project.findMarkers(JptJpaCorePlugin.VALIDATION_MARKER_ID, true, IResource.DEPTH_INFINITE);
		for (IMarker marker : markers) {
			marker.delete();
		}
	}

	private void validate(IReporter reporter, IProject project) {
		Iterable<IMessage> messages = this.buildValidationMessages(reporter, project);
		
		// since the validation messages are usually built asynchronously
		// and a workspace shutdown could occur in the meantime,
		// wait until we actually get the new messages before we clear out the old messages
		this.clearMarkers(project);
		
		JpaValidationPreferencesManager prefsManager = new JpaValidationPreferencesManager(project);
		for (IMessage message : messages) {
			// check preferences for IGNORE
			if (prefsManager.problemIsNotIgnored(message.getId())) {
				reporter.addMessage(this, message);
			}
		}
	}

	private Iterable<IMessage> buildValidationMessages(IReporter reporter, IProject project) {
		try {
			return this.buildValidationMessages_(reporter, project);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(ex);
		}
	}

	private Iterable<IMessage> buildValidationMessages_(IReporter reporter, IProject project) throws InterruptedException {
		return this.getJpaProjectReference(project).buildValidationMessages(reporter);
	}

	private JpaProject.Reference getJpaProjectReference(IProject project) {
		return (JpaProject.Reference) project.getAdapter(JpaProject.Reference.class);
	}
}
