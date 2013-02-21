/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.validation;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageTools;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbProjectManager;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.core.internal.plugin.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.validation.JptJaxbCoreValidationMessages;
import org.eclipse.wst.validation.AbstractValidator;
import org.eclipse.wst.validation.ValidationResult;
import org.eclipse.wst.validation.ValidationState;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IProjectValidationContext;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.wst.validation.internal.provisional.core.IValidationContext;
import org.eclipse.wst.validation.internal.provisional.core.IValidator;

/**
 * This class is referenced in the JAXB extension for the
 * WTP validator extension point.
 * <p>
 * See <code>org.eclipse.jpt.jaxb.core/plugin.xml:org.eclipse.wst.validation.validatorV2</code>.
 */
public class JaxbValidator
	extends AbstractValidator
	implements IValidator
{
	private static final String OLD_RELATIVE_MARKER_TYPE = "jaxbProblemMarker";  //$NON-NLS-1$
	private static final String OLD_MARKER_TYPE = JaxbProject.MARKER_TYPE_SCOPE_ + OLD_RELATIVE_MARKER_TYPE;


	public JaxbValidator() {
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
		Iterable<IMessage> messages = this.buildNonIgnoredValidationMessages(reporter, project);

		// since the validation messages are usually built asynchronously
		// and a workspace shutdown could occur in the meantime,
		// wait until we actually get the new messages before we clear out the old messages
		this.clearMarkers(project);

		for (IMessage message : messages) {
			reporter.addMessage(this, message);
		}
	}

	/**
	 * Filter out any message with an "ignore" severity (which would be
	 * specified in the preferences).
	 */
	private Iterable<IMessage> buildNonIgnoredValidationMessages(IReporter reporter, IProject project) {
		return IterableTools.filter(this.buildValidationMessages(reporter, project), NON_IGNORED_MESSAGE);
	}

	private static final Predicate<IMessage> NON_IGNORED_MESSAGE = new NonIgnoredMessage();
	/* CU private */ static class NonIgnoredMessage
		extends Predicate.Adapter<IMessage>
	{
		@Override
		public boolean evaluate(IMessage message) {
			return message.getSeverity() != -1;
		}
	}

	private Iterable<IMessage> buildValidationMessages(IReporter reporter, IProject project) {
		JaxbProject jaxbProject = this.getJaxbProject(project);
		return (jaxbProject != null) ?
			jaxbProject.getValidationMessages(reporter) :
			this.buildValidationFailedMessages(project);
	}

	private JaxbProject getJaxbProject(IProject project) {
		JaxbProjectManager jaxbProjectManager = this.getJaxbProjectManager();
		return (jaxbProjectManager == null) ? null : jaxbProjectManager.getJaxbProject(project);
	}

	private JaxbProjectManager getJaxbProjectManager() {
		JaxbWorkspace jaxbWorkspace = this.getJaxbWorkspace();
		return (jaxbWorkspace == null) ? null : this.getJaxbWorkspace().getJaxbProjectManager();
	}

	private JaxbWorkspace getJaxbWorkspace() {
		return (JaxbWorkspace) ResourcesPlugin.getWorkspace().getAdapter(JaxbWorkspace.class);
	}

	private Iterable<IMessage> buildValidationFailedMessages(IProject project) {
		return IterableTools.singletonIterable(this.buildValidationFailedMessage(project));
	}

	private IMessage buildValidationFailedMessage(IProject project) {
		return ValidationMessageTools.buildErrorValidationMessage(
					project,
					JptJaxbCoreValidationMessages.JAXB_VALIDATION_FAILED
				);
	}

	private void clearMarkers(IProject project) {
		try {
			this.clearMarkers_(project);
		} catch (CoreException ex) {
			JptJaxbCorePlugin.instance().logError(ex);
		}
	}

	private void clearMarkers_(IProject project) throws CoreException {
		IMarker[] markers = project.findMarkers(JaxbProject.MARKER_TYPE, true, IResource.DEPTH_INFINITE);
		// TODO post-Kepler: remove this line of code
		ArrayTools.addAll(markers, project.findMarkers(OLD_MARKER_TYPE, true, IResource.DEPTH_INFINITE));
		for (IMarker marker : markers) {
			marker.delete();
		}
	}
}
