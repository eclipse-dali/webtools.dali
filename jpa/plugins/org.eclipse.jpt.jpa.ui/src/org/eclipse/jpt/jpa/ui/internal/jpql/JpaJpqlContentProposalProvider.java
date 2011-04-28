/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpql;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionInfo;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.Trigger;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.SWTKeySupport;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.internal.text.html.HTMLTextPresenter;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.DefaultTextHover;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.DefaultAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jpt.common.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.persistence.jpa.jpql.JPQLQueryProblem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.editors.text.EditorsPlugin;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.swt.IFocusService;
import org.eclipse.ui.texteditor.AnnotationPreference;
import org.eclipse.ui.texteditor.DefaultMarkerAnnotationAccess;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;

/**
 * This provider installs content assist support on a {@link StyledText} widget in order to give
 * choices at any given position within a JPQL query.
 * <p>
 * TODO. Add syntax highlight for the JPQL identifiers.
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
@SuppressWarnings({"nls", "restriction"})
public final class JpaJpqlContentProposalProvider extends JpqlCompletionProposalComputer<ICompletionProposal> {

	/**
	 * This model holds onto the {@link Annotation annotations} that have been created for each
	 * {@link JPQLQueryProblem}
	 */
	private AnnotationModel annotationModel;

	/**
	 * The decoration support required to paint the {@link Annotation annotations}, which are the
	 * JPQL problems.
	 */
	private SourceViewerDecorationSupport decorationSupport;

	/**
	 * This handler will trigger an event that will be used to notify the {@link SourceViewer} to
	 * invoke the content assist.
	 */
	private IHandlerActivation handlerActivation;

	/**
	 * The position within the JPQL query.
	 */
	private int position;

	/**
	 * The holder of the named query.
	 */
	private PropertyValueModel<? extends NamedQuery> queryHolder;

	/**
	 * The {@link ResourceBundle} that contains the JPQL problems.
	 */
	private ResourceBundle resourceBundle;

	/**
	 * This viewer is used to install various functionality over the {@link StyledText}.
	 */
	private SourceViewer sourceViewer;

	/**
	 * The configuration object used to configure the {@link SourceViewer}.
	 */
	private JpqlSourceViewerConfiguration sourceViewerConfiguration;

	/**
	 * The widget used to display the JPQL query.
	 */
	private StyledText styledText;

	/**
	 * This listener is used to dispose the {@link org.eclipse.persistence.jpa.jpql.JPQLQueryHelper
	 * JPQLQueryHelper} when the subject changes and to reset the undo manager in order to prevent
	 * the query from being entirely deleted with an undo.
	 */
	private PropertyChangeListener subjectChangeListener;

	/**
	 * The holder of the JPQL query.
	 */
	private WritablePropertyValueModel<String> textHolder;

	/**
	 * Listens to the JPQL query and keep the {@link Document} in sync.
	 */
	private PropertyChangeListener textListener;

	/**
	 * The unique identifier - appended by hashCode() - used to register the widget with the focus
	 * handler.
	 */
	private static final String CONTROL_ID = "jpql.focus.control";

	/**
	 * The unique identifier used to mark an {@link Annotation} as a JPQL problem.
	 */
	private static final String ERROR_TYPE = "org.eclipse.jdt.ui.error";

	/**
	 * Creates a new <code>JpaJpqlContentProposalProvider</code>.
	 *
	 * @param parent The parent {@link Composite} where to add the JPQL query editor
	 * @param queryHolder The holder of the named query
	 * @param textHolder The holder of the JPQL query
	 */
	public JpaJpqlContentProposalProvider(Composite parent,
	                                      PropertyValueModel<? extends NamedQuery> queryHolder,
	                                      WritablePropertyValueModel<String> textHolder) {

		super();
		initialize(parent, queryHolder, textHolder);
	}

	private void activateHandler() {

		IWorkbench workbench = PlatformUI.getWorkbench();
		IFocusService focusService = (IFocusService) workbench.getService(IFocusService.class);
		IHandlerService handlerService = (IHandlerService) workbench.getService(IHandlerService.class);

		if ((focusService != null) && (handlerService != null)) {

			focusService.addFocusTracker(styledText, CONTROL_ID + hashCode());

			Expression expression = buildExpression();
			IHandler handler = buildHandler();

			handlerActivation = handlerService.activateHandler(
				ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS,
				handler,
				expression
			);

			handlerActivation = handlerService.activateHandler(
				ActionFactory.UNDO.getCommandId(),
				handler,
				expression
			);

			handlerActivation = handlerService.activateHandler(
				ActionFactory.REDO.getCommandId(),
				handler,
				expression
			);
		}
	}

	@SuppressWarnings("unchecked")
	private List<AnnotationPreference> annotationPreferences() {
		return EditorsPlugin.getDefault().getMarkerAnnotationPreferences().getAnnotationPreferences();
	}

	private DisposeListener buildDisposeListener() {
		return new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				dispose();
			}
		};
	}

	private IDocumentListener buildDocumentListener() {
		return new IDocumentListener() {
			public void documentAboutToBeChanged(DocumentEvent event) {
			}
			public void documentChanged(DocumentEvent event) {
				try {
					IDocument document = event.getDocument();
					String text = document.get(0, document.getLength());
					textHolder.setValue(text);
				}
				catch (BadLocationException e) {
					// Simply ignore, should never happen
				}
			}
		};
	}

	private Expression buildExpression() {
		return new Expression() {
			@Override
			public void collectExpressionInfo(ExpressionInfo info) {
				info.addVariableNameAccess(ISources.ACTIVE_FOCUS_CONTROL_NAME);
			}
			@Override
			public EvaluationResult evaluate(IEvaluationContext context) {
				Object variable = context.getVariable(ISources.ACTIVE_FOCUS_CONTROL_NAME);
				return (variable == styledText) ? EvaluationResult.TRUE : EvaluationResult.FALSE;
			}
		};
	}

	private FocusListener buildFocusListener() {
		return new FocusListener() {
			public void focusGained(FocusEvent e) {
			}
			public void focusLost(FocusEvent e) {
				// Only dispose the query helper if the content proposal popup doesn't grab the focus
				if (!sourceViewerConfiguration.contentAssistant.hasProposalPopupFocus()) {
					disposeQueryHelper();
				}
			}
		};
	}

	private IHandler buildHandler() {
		return new AbstractHandler() {
			public Object execute(ExecutionEvent event) throws ExecutionException {
				String commandId = event.getCommand().getId();

				// Content Assist
				if (ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS.equals(commandId)) {
					sourceViewer.doOperation(ISourceViewer.CONTENTASSIST_PROPOSALS);
				}
				// Undo
				else if (ActionFactory.UNDO.getCommandId().equals(commandId)) {
					if (sourceViewer.getUndoManager().undoable()) {
						sourceViewer.getUndoManager().undo();
					}
				}
				// Redo
				else if (ActionFactory.REDO.getCommandId().equals(commandId)) {
					if (sourceViewer.getUndoManager().redoable()) {
						sourceViewer.getUndoManager().redo();
					}
				}

				return null;
			}
		};
	}

	private String buildMessage(JPQLQueryProblem problem) {
		String message = resourceBundle().getString(problem.getMessageKey());
		message = MessageFormat.format(message, (Object[]) problem.getMessageArguments());
		return message;
	}

	private ModifyListener buildModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		};
	}

	private Comparator<JPQLQueryProblem> buildProblemComparator() {
		return new Comparator<JPQLQueryProblem>() {
			public int compare(JPQLQueryProblem problem1, JPQLQueryProblem problem2) {
				int result = problem1.getStartPosition() - problem2.getStartPosition();
				if (result == 0) {
					result = problem1.getEndPosition() - problem2.getEndPosition();
				}
				return result;
			}
		};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	ICompletionProposal buildProposal(String proposal,
	                                  String displayString,
	                                  String additionalInfo,
	                                  Image image,
	                                  int cursorOffset) {

		return new JpqlCompletionProposal(
			contentAssistProposals,
			proposal,
			displayString,
			additionalInfo,
			image,
			namedQuery,
			actualQuery,
			jpqlQuery,
			offset,
			position,
			cursorOffset,
			false
		);
	}

	private PropertyChangeListener buildSubjectChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				subjectChanged(e);
			}
		};
	}

	private PropertyChangeListener buildTextListener() {
		return new SWTPropertyChangeListenerWrapper(new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				String text = (String) event.getNewValue();
				if (text == null) {
					text = StringTools.EMPTY_STRING;
				}
				if (!styledText.getText().equals(text)) {
					sourceViewer.getDocument().set(text);
				}
			}
		});
	}

	private Image contentAssistImage() {
		FieldDecorationRegistry registry = FieldDecorationRegistry.getDefault();
		return registry.getFieldDecoration(FieldDecorationRegistry.DEC_CONTENT_PROPOSAL).getImage();
	}

	/**
	 * Makes sure the {@link Color} used to paint the underlying problem is disposed when the
	 * {@link StyledText} widget is disposed.
	 */
	private void dispose() {

		sessionEnded();

		decorationSupport.dispose();
		textHolder.removePropertyChangeListener(PropertyValueModel.VALUE, textListener);
		queryHolder.removePropertyChangeListener(PropertyValueModel.VALUE, subjectChangeListener);

		IWorkbench workbench = PlatformUI.getWorkbench();
		IHandlerService handlerService = (IHandlerService) workbench.getService(IHandlerService.class);
		handlerService.deactivateHandler(handlerActivation);
	}

	private void disposeQueryHelper() {
		queryHelper.dispose();
		queryHelper.disposeProvider();
	}

	private KeyStroke findContentAssistTrigger() {

		IBindingService bindingService = (IBindingService) PlatformUI.getWorkbench().getService(IBindingService.class);

		// Dig through the list of available bindings to find the one for content assist
		for (Binding binding : bindingService.getBindings()) {
			if (isContentAssistBinding(binding)) {
				Trigger[] triggers = binding.getTriggerSequence().getTriggers();
				if ((triggers != null) && (triggers.length > 0)) {
					return (KeyStroke) triggers[0];
				}
			}
		}

		// The default trigger was not found, use the default
		return KeyStroke.getInstance(SWT.CTRL, ' ');
	}

	/**
	 * Returns the widget used to display the JPQL query.
	 *
	 * @return The main widget
	 */
	public StyledText getStyledText() {
		return styledText;
	}

	private void initialize(Composite parent,
	                        PropertyValueModel<? extends NamedQuery> queryHolder,
	                        WritablePropertyValueModel<String> textHolder) {

		this.queryHolder     = queryHolder;
		this.annotationModel = new AnnotationModel();
		this.textHolder      = textHolder;

		// Make sure the StyledText is kept in sync with the text holder
		textListener = buildTextListener();
		textHolder.addPropertyChangeListener(PropertyValueModel.VALUE, textListener);

		// Make sure the user can't delete the entire query when doing undo
		subjectChangeListener = buildSubjectChangeListener();
		queryHolder.addPropertyChangeListener(PropertyValueModel.VALUE, subjectChangeListener);

		// Create the SourceViewer, which is responsible for everything: content assist, tool tip
		// hovering over the annotation (problems), etc
		sourceViewer = new SourceViewer(parent, null, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FLAT);
		sourceViewerConfiguration = new JpqlSourceViewerConfiguration();
		sourceViewer.configure(sourceViewerConfiguration);
		sourceViewer.setDocument(new Document(), annotationModel);
		sourceViewer.getDocument().addDocumentListener(buildDocumentListener());

		styledText = sourceViewer.getTextWidget();
		styledText.addFocusListener(buildFocusListener());
		styledText.addModifyListener(buildModifyListener());
		styledText.addDisposeListener(buildDisposeListener());

		// Add the support for painting the annotations (JPQL problems) in the source viewer
		installDecorationSupport();

		// Bind the content assist key trigger with the handler service
		activateHandler();

		// Install a custom context menu to the widget
		TextTransferHandler.installContextMenu(styledText, sourceViewer.getUndoManager());
	}

	/**
	 * Installs the content assist icon at the top left of the {@link StyledText}.
	 */
	public void installControlDecoration() {

		// Retrieve the content assist trigger
		KeyStroke contentAssistTrigger = findContentAssistTrigger();
		String key = SWTKeySupport.getKeyFormatterForPlatform().format(contentAssistTrigger);

		// Add the context assist icon at the top left corner of the StyledText
		ControlDecoration decoration = new ControlDecoration(styledText, SWT.LEFT | SWT.TOP);
		decoration.setDescriptionText(NLS.bind(JptUiMessages.JpqlContentProposalProvider_Description, key));
		decoration.setImage(contentAssistImage());
		decoration.setShowOnlyOnFocus(true);
	}

	private void installDecorationSupport() {

		decorationSupport = new SourceViewerDecorationSupport(
			sourceViewer,
			null,
			new DefaultMarkerAnnotationAccess(),
			EditorsPlugin.getDefault().getSharedTextColors()
		);

		for (AnnotationPreference preference : annotationPreferences()) {
			decorationSupport.setAnnotationPreference(preference);
		}

		decorationSupport.install(EditorsPlugin.getDefault().getPreferenceStore());
	}

	private boolean isContentAssistBinding(Binding binding) {

		ParameterizedCommand command = binding.getParameterizedCommand();

		return command != null &&
		       command.getCommand() != null &&
		       command.getCommand().getId().equals(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
	}

	private NamedQuery query() {
		return queryHolder.getValue();
	}

	private ResourceBundle resourceBundle() {
		if (resourceBundle == null) {
			resourceBundle = ResourceBundle.getBundle(
				"jpa_jpql_validation",
				Locale.getDefault(),
				JptJpaCorePlugin.class.getClassLoader()
			);
		}
		return resourceBundle;
	}

	private List<JPQLQueryProblem> sortProblems(List<JPQLQueryProblem> problems) {
		Collections.sort(problems, buildProblemComparator());
		return problems;
	}

	private void subjectChanged(PropertyChangeEvent e) {

		// Disposes of the internal data since the subject changed
		disposeQueryHelper();

		// Prevent undoing the actual query that was set
		if (e.getNewValue() != null) {
			sourceViewer.getUndoManager().reset();
			validate();
		}
		else {
			annotationModel.removeAllAnnotations();
		}
	}

	/**
	 * Validates the given JPQL query and add highlights where problems have been found.
	 */
	private void validate() {

		NamedQuery query = query();
		annotationModel.removeAllAnnotations();

		if ((query != null) && !styledText.isDisposed()) {
			try {
				String jpqlQuery = styledText.getText();
				queryHelper.setQuery(query, jpqlQuery);
				String parsedJpqlQuery = queryHelper.getParsedJPQLQuery();

				for (JPQLQueryProblem problem : sortProblems(queryHelper.validate())) {

					// Create the range
					int[] positions = queryHelper.buildPositions(problem, parsedJpqlQuery, jpqlQuery);

					// Add the problem to the tool tip
					Annotation annotation = new Annotation(ERROR_TYPE, true, buildMessage(problem));
					annotationModel.addAnnotation(annotation, new Position(positions[0], positions[1] - positions[0]));
				}
			}
			finally {
				queryHelper.dispose();
			}
		}
	}

	/**
	 * This processor is responsible to create the list of {@link ICompletionProposal proposals}
	 * based on the position of the cursor within the query.
	 */
	private class ContentAssistProcessor implements IContentAssistProcessor {

		/**
		 * {@inheritDoc}
		 */
		public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {

			JpaJpqlContentProposalProvider.this.position = offset;

			String jpqlQuery = viewer.getDocument().get();
			List<ICompletionProposal> proposals = buildProposals(query(), jpqlQuery, 0, position);
			return proposals.toArray(new ICompletionProposal[proposals.size()]);
		}

		/**
		 * {@inheritDoc}
		 */
		public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		public char[] getCompletionProposalAutoActivationCharacters() {
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		public char[] getContextInformationAutoActivationCharacters() {
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		public IContextInformationValidator getContextInformationValidator() {
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		public String getErrorMessage() {
			return null;
		}
	}

	private class JpqlSourceViewerConfiguration extends SourceViewerConfiguration {

		/**
		 * Keeps track of the content assist since we need to know when the popup is opened or not.
		 */
		ContentAssistant contentAssistant;

		private IInformationControlCreator buildInformationControlCreator() {
			return new IInformationControlCreator() {
				public IInformationControl createInformationControl(Shell parent) {
					return new DefaultInformationControl(
						parent,
						EditorsUI.getTooltipAffordanceString(),
						new HTMLTextPresenter(true)
					);
				}
			};
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
			return new DefaultAnnotationHover();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
			contentAssistant = new ContentAssistant();
			contentAssistant.setContentAssistProcessor(new ContentAssistProcessor(), IDocument.DEFAULT_CONTENT_TYPE);
			contentAssistant.setInformationControlCreator(buildInformationControlCreator());
			return contentAssistant;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType) {
			return new DefaultTextHover(sourceViewer);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType, int stateMask) {
			return new DefaultTextHover(sourceViewer);
		}
	}
}