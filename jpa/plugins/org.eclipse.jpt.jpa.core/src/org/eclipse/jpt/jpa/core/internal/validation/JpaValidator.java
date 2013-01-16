/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.validation;

import java.util.ArrayList;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
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
	implements IValidator
{

	public static final String RELATIVE_MARKER_ID = "jpaProblemMarker";  //$NON-NLS-1$

	/**
	 * The identifier for the JPA validation marker
	 * (value <code>"org.eclipse.jpt.jpa.core.jpaProblemMarker"</code>).
	 * <p>
	 * See <code>org.eclipse.jpt.jpa.core/plugin.xml:org.eclipse.core.resources.markers</code>.
	 */
	public static final String MARKER_ID = JptJpaCorePlugin.instance().getPluginID() + '.' + RELATIVE_MARKER_ID;

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

	private void validate(IReporter reporter, IProject project) {
		Iterable<IMessage> allMessages = this.buildValidationMessages(reporter, project);

		ArrayList<IMessage> messages = new ArrayList<IMessage>();
		for (IMessage message : allMessages) {
			// check preferences for IGNORE
			if (ObjectTools.notEquals(JpaPreferences.getProblemSeverity(project, message.getId()), JpaPreferences.PROBLEM_IGNORE)) {
				messages.add(message);
			}
		}

		// since the validation messages are usually built asynchronously
		// and a workspace shutdown could occur in the meantime,
		// wait until we actually get the new messages before we clear out the old messages
		this.clearMarkers(project);
		
		for (IMessage message : messages) {
			reporter.addMessage(this, message);
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
		JpaProject.Reference ref = this.getJpaProjectReference(project);
		return (ref != null) ? ref.buildValidationMessages(reporter) : this.buildValidationFailedMessages(project);
	}

	/**
	 * This can happen when validation is executed during workbench shutdown
	 * (e.g. when the user "exits and saves")
	 * and the Dali plug-ins have been "stopped" before this validator is invoked.
	 */
	private Iterable<IMessage> buildValidationFailedMessages(IProject project) {
		return new SingleElementIterable<IMessage>(this.buildValidationFailedMessage(project));
	}

	private IMessage buildValidationFailedMessage(IProject project) {
		return DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.JPA_VALIDATION_FAILED,
					project
				);
	}

	private JpaProject.Reference getJpaProjectReference(IProject project) {
		return (JpaProject.Reference) project.getAdapter(JpaProject.Reference.class);
	}

	private void clearMarkers(IProject project) {
		try {
			this.clearMarkers_(project);
		} catch (CoreException ex) {
			JptJpaCorePlugin.instance().logError(ex);
		}
	}

	private void clearMarkers_(IProject project) throws CoreException {
		IMarker[] markers = project.findMarkers(MARKER_ID, true, IResource.DEPTH_INFINITE);
		for (IMarker marker : markers) {
			marker.delete();
		}
	}
}
