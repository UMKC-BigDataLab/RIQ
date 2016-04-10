// Generated from SparqlParser.g4 by ANTLR 4.4
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SparqlParser}.
 */
public interface SparqlParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SparqlParser#quadPattern}.
	 * @param ctx the parse tree
	 */
	void enterQuadPattern(@NotNull SparqlParser.QuadPatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#quadPattern}.
	 * @param ctx the parse tree
	 */
	void exitQuadPattern(@NotNull SparqlParser.QuadPatternContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryMultiplicativeExpression}
	 * labeled alternative in {@link SparqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryMultiplicativeExpression(@NotNull SparqlParser.UnaryMultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryMultiplicativeExpression}
	 * labeled alternative in {@link SparqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryMultiplicativeExpression(@NotNull SparqlParser.UnaryMultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#aggregate}.
	 * @param ctx the parse tree
	 */
	void enterAggregate(@NotNull SparqlParser.AggregateContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#aggregate}.
	 * @param ctx the parse tree
	 */
	void exitAggregate(@NotNull SparqlParser.AggregateContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#updateCommand}.
	 * @param ctx the parse tree
	 */
	void enterUpdateCommand(@NotNull SparqlParser.UpdateCommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#updateCommand}.
	 * @param ctx the parse tree
	 */
	void exitUpdateCommand(@NotNull SparqlParser.UpdateCommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#limitOffsetClauses}.
	 * @param ctx the parse tree
	 */
	void enterLimitOffsetClauses(@NotNull SparqlParser.LimitOffsetClausesContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#limitOffsetClauses}.
	 * @param ctx the parse tree
	 */
	void exitLimitOffsetClauses(@NotNull SparqlParser.LimitOffsetClausesContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#argList}.
	 * @param ctx the parse tree
	 */
	void enterArgList(@NotNull SparqlParser.ArgListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#argList}.
	 * @param ctx the parse tree
	 */
	void exitArgList(@NotNull SparqlParser.ArgListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#verbPath}.
	 * @param ctx the parse tree
	 */
	void enterVerbPath(@NotNull SparqlParser.VerbPathContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#verbPath}.
	 * @param ctx the parse tree
	 */
	void exitVerbPath(@NotNull SparqlParser.VerbPathContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#prefixDecl}.
	 * @param ctx the parse tree
	 */
	void enterPrefixDecl(@NotNull SparqlParser.PrefixDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#prefixDecl}.
	 * @param ctx the parse tree
	 */
	void exitPrefixDecl(@NotNull SparqlParser.PrefixDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(@NotNull SparqlParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(@NotNull SparqlParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#collectionPath}.
	 * @param ctx the parse tree
	 */
	void enterCollectionPath(@NotNull SparqlParser.CollectionPathContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#collectionPath}.
	 * @param ctx the parse tree
	 */
	void exitCollectionPath(@NotNull SparqlParser.CollectionPathContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#triplesTemplate}.
	 * @param ctx the parse tree
	 */
	void enterTriplesTemplate(@NotNull SparqlParser.TriplesTemplateContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#triplesTemplate}.
	 * @param ctx the parse tree
	 */
	void exitTriplesTemplate(@NotNull SparqlParser.TriplesTemplateContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#inlineData}.
	 * @param ctx the parse tree
	 */
	void enterInlineData(@NotNull SparqlParser.InlineDataContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#inlineData}.
	 * @param ctx the parse tree
	 */
	void exitInlineData(@NotNull SparqlParser.InlineDataContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#selectVariables}.
	 * @param ctx the parse tree
	 */
	void enterSelectVariables(@NotNull SparqlParser.SelectVariablesContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#selectVariables}.
	 * @param ctx the parse tree
	 */
	void exitSelectVariables(@NotNull SparqlParser.SelectVariablesContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#solutionModifier}.
	 * @param ctx the parse tree
	 */
	void enterSolutionModifier(@NotNull SparqlParser.SolutionModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#solutionModifier}.
	 * @param ctx the parse tree
	 */
	void exitSolutionModifier(@NotNull SparqlParser.SolutionModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#orderCondition}.
	 * @param ctx the parse tree
	 */
	void enterOrderCondition(@NotNull SparqlParser.OrderConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#orderCondition}.
	 * @param ctx the parse tree
	 */
	void exitOrderCondition(@NotNull SparqlParser.OrderConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#graphOrDefault}.
	 * @param ctx the parse tree
	 */
	void enterGraphOrDefault(@NotNull SparqlParser.GraphOrDefaultContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#graphOrDefault}.
	 * @param ctx the parse tree
	 */
	void exitGraphOrDefault(@NotNull SparqlParser.GraphOrDefaultContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#move}.
	 * @param ctx the parse tree
	 */
	void enterMove(@NotNull SparqlParser.MoveContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#move}.
	 * @param ctx the parse tree
	 */
	void exitMove(@NotNull SparqlParser.MoveContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#copy}.
	 * @param ctx the parse tree
	 */
	void enterCopy(@NotNull SparqlParser.CopyContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#copy}.
	 * @param ctx the parse tree
	 */
	void exitCopy(@NotNull SparqlParser.CopyContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#prefixedName}.
	 * @param ctx the parse tree
	 */
	void enterPrefixedName(@NotNull SparqlParser.PrefixedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#prefixedName}.
	 * @param ctx the parse tree
	 */
	void exitPrefixedName(@NotNull SparqlParser.PrefixedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#constructQuery}.
	 * @param ctx the parse tree
	 */
	void enterConstructQuery(@NotNull SparqlParser.ConstructQueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#constructQuery}.
	 * @param ctx the parse tree
	 */
	void exitConstructQuery(@NotNull SparqlParser.ConstructQueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#strReplaceExpression}.
	 * @param ctx the parse tree
	 */
	void enterStrReplaceExpression(@NotNull SparqlParser.StrReplaceExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#strReplaceExpression}.
	 * @param ctx the parse tree
	 */
	void exitStrReplaceExpression(@NotNull SparqlParser.StrReplaceExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#groupGraphPatternSub}.
	 * @param ctx the parse tree
	 */
	void enterGroupGraphPatternSub(@NotNull SparqlParser.GroupGraphPatternSubContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#groupGraphPatternSub}.
	 * @param ctx the parse tree
	 */
	void exitGroupGraphPatternSub(@NotNull SparqlParser.GroupGraphPatternSubContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#objectPath}.
	 * @param ctx the parse tree
	 */
	void enterObjectPath(@NotNull SparqlParser.ObjectPathContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#objectPath}.
	 * @param ctx the parse tree
	 */
	void exitObjectPath(@NotNull SparqlParser.ObjectPathContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#graphRef}.
	 * @param ctx the parse tree
	 */
	void enterGraphRef(@NotNull SparqlParser.GraphRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#graphRef}.
	 * @param ctx the parse tree
	 */
	void exitGraphRef(@NotNull SparqlParser.GraphRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#builtInCall}.
	 * @param ctx the parse tree
	 */
	void enterBuiltInCall(@NotNull SparqlParser.BuiltInCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#builtInCall}.
	 * @param ctx the parse tree
	 */
	void exitBuiltInCall(@NotNull SparqlParser.BuiltInCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#selectQuery}.
	 * @param ctx the parse tree
	 */
	void enterSelectQuery(@NotNull SparqlParser.SelectQueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#selectQuery}.
	 * @param ctx the parse tree
	 */
	void exitSelectQuery(@NotNull SparqlParser.SelectQueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#create}.
	 * @param ctx the parse tree
	 */
	void enterCreate(@NotNull SparqlParser.CreateContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#create}.
	 * @param ctx the parse tree
	 */
	void exitCreate(@NotNull SparqlParser.CreateContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#varOrIRI}.
	 * @param ctx the parse tree
	 */
	void enterVarOrIRI(@NotNull SparqlParser.VarOrIRIContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#varOrIRI}.
	 * @param ctx the parse tree
	 */
	void exitVarOrIRI(@NotNull SparqlParser.VarOrIRIContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#quadsNotTriples}.
	 * @param ctx the parse tree
	 */
	void enterQuadsNotTriples(@NotNull SparqlParser.QuadsNotTriplesContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#quadsNotTriples}.
	 * @param ctx the parse tree
	 */
	void exitQuadsNotTriples(@NotNull SparqlParser.QuadsNotTriplesContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#havingCondition}.
	 * @param ctx the parse tree
	 */
	void enterHavingCondition(@NotNull SparqlParser.HavingConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#havingCondition}.
	 * @param ctx the parse tree
	 */
	void exitHavingCondition(@NotNull SparqlParser.HavingConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#constructTriples}.
	 * @param ctx the parse tree
	 */
	void enterConstructTriples(@NotNull SparqlParser.ConstructTriplesContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#constructTriples}.
	 * @param ctx the parse tree
	 */
	void exitConstructTriples(@NotNull SparqlParser.ConstructTriplesContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#serviceGraphPattern}.
	 * @param ctx the parse tree
	 */
	void enterServiceGraphPattern(@NotNull SparqlParser.ServiceGraphPatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#serviceGraphPattern}.
	 * @param ctx the parse tree
	 */
	void exitServiceGraphPattern(@NotNull SparqlParser.ServiceGraphPatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#load}.
	 * @param ctx the parse tree
	 */
	void enterLoad(@NotNull SparqlParser.LoadContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#load}.
	 * @param ctx the parse tree
	 */
	void exitLoad(@NotNull SparqlParser.LoadContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#graphNodePath}.
	 * @param ctx the parse tree
	 */
	void enterGraphNodePath(@NotNull SparqlParser.GraphNodePathContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#graphNodePath}.
	 * @param ctx the parse tree
	 */
	void exitGraphNodePath(@NotNull SparqlParser.GraphNodePathContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#deleteClause}.
	 * @param ctx the parse tree
	 */
	void enterDeleteClause(@NotNull SparqlParser.DeleteClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#deleteClause}.
	 * @param ctx the parse tree
	 */
	void exitDeleteClause(@NotNull SparqlParser.DeleteClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#groupOrUnionGraphPattern}.
	 * @param ctx the parse tree
	 */
	void enterGroupOrUnionGraphPattern(@NotNull SparqlParser.GroupOrUnionGraphPatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#groupOrUnionGraphPattern}.
	 * @param ctx the parse tree
	 */
	void exitGroupOrUnionGraphPattern(@NotNull SparqlParser.GroupOrUnionGraphPatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#rdfLiteral}.
	 * @param ctx the parse tree
	 */
	void enterRdfLiteral(@NotNull SparqlParser.RdfLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#rdfLiteral}.
	 * @param ctx the parse tree
	 */
	void exitRdfLiteral(@NotNull SparqlParser.RdfLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(@NotNull SparqlParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(@NotNull SparqlParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#graphTerm}.
	 * @param ctx the parse tree
	 */
	void enterGraphTerm(@NotNull SparqlParser.GraphTermContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#graphTerm}.
	 * @param ctx the parse tree
	 */
	void exitGraphTerm(@NotNull SparqlParser.GraphTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#quadsDetails}.
	 * @param ctx the parse tree
	 */
	void enterQuadsDetails(@NotNull SparqlParser.QuadsDetailsContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#quadsDetails}.
	 * @param ctx the parse tree
	 */
	void exitQuadsDetails(@NotNull SparqlParser.QuadsDetailsContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#subSelect}.
	 * @param ctx the parse tree
	 */
	void enterSubSelect(@NotNull SparqlParser.SubSelectContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#subSelect}.
	 * @param ctx the parse tree
	 */
	void exitSubSelect(@NotNull SparqlParser.SubSelectContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#constraint}.
	 * @param ctx the parse tree
	 */
	void enterConstraint(@NotNull SparqlParser.ConstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#constraint}.
	 * @param ctx the parse tree
	 */
	void exitConstraint(@NotNull SparqlParser.ConstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#collection}.
	 * @param ctx the parse tree
	 */
	void enterCollection(@NotNull SparqlParser.CollectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#collection}.
	 * @param ctx the parse tree
	 */
	void exitCollection(@NotNull SparqlParser.CollectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#havingClause}.
	 * @param ctx the parse tree
	 */
	void enterHavingClause(@NotNull SparqlParser.HavingClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#havingClause}.
	 * @param ctx the parse tree
	 */
	void exitHavingClause(@NotNull SparqlParser.HavingClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#clear}.
	 * @param ctx the parse tree
	 */
	void enterClear(@NotNull SparqlParser.ClearContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#clear}.
	 * @param ctx the parse tree
	 */
	void exitClear(@NotNull SparqlParser.ClearContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#triplesBlock}.
	 * @param ctx the parse tree
	 */
	void enterTriplesBlock(@NotNull SparqlParser.TriplesBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#triplesBlock}.
	 * @param ctx the parse tree
	 */
	void exitTriplesBlock(@NotNull SparqlParser.TriplesBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#pathMod}.
	 * @param ctx the parse tree
	 */
	void enterPathMod(@NotNull SparqlParser.PathModContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#pathMod}.
	 * @param ctx the parse tree
	 */
	void exitPathMod(@NotNull SparqlParser.PathModContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#orderClause}.
	 * @param ctx the parse tree
	 */
	void enterOrderClause(@NotNull SparqlParser.OrderClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#orderClause}.
	 * @param ctx the parse tree
	 */
	void exitOrderClause(@NotNull SparqlParser.OrderClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#groupGraphPattern}.
	 * @param ctx the parse tree
	 */
	void enterGroupGraphPattern(@NotNull SparqlParser.GroupGraphPatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#groupGraphPattern}.
	 * @param ctx the parse tree
	 */
	void exitGroupGraphPattern(@NotNull SparqlParser.GroupGraphPatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#quadData}.
	 * @param ctx the parse tree
	 */
	void enterQuadData(@NotNull SparqlParser.QuadDataContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#quadData}.
	 * @param ctx the parse tree
	 */
	void exitQuadData(@NotNull SparqlParser.QuadDataContext ctx);
	/**
	 * Enter a parse tree produced by the {@code baseExpression}
	 * labeled alternative in {@link SparqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBaseExpression(@NotNull SparqlParser.BaseExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code baseExpression}
	 * labeled alternative in {@link SparqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBaseExpression(@NotNull SparqlParser.BaseExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#usingClause}.
	 * @param ctx the parse tree
	 */
	void enterUsingClause(@NotNull SparqlParser.UsingClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#usingClause}.
	 * @param ctx the parse tree
	 */
	void exitUsingClause(@NotNull SparqlParser.UsingClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#numericLiteralPositive}.
	 * @param ctx the parse tree
	 */
	void enterNumericLiteralPositive(@NotNull SparqlParser.NumericLiteralPositiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#numericLiteralPositive}.
	 * @param ctx the parse tree
	 */
	void exitNumericLiteralPositive(@NotNull SparqlParser.NumericLiteralPositiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#numericLiteral}.
	 * @param ctx the parse tree
	 */
	void enterNumericLiteral(@NotNull SparqlParser.NumericLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#numericLiteral}.
	 * @param ctx the parse tree
	 */
	void exitNumericLiteral(@NotNull SparqlParser.NumericLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#iriRefOrFunction}.
	 * @param ctx the parse tree
	 */
	void enterIriRefOrFunction(@NotNull SparqlParser.IriRefOrFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#iriRefOrFunction}.
	 * @param ctx the parse tree
	 */
	void exitIriRefOrFunction(@NotNull SparqlParser.IriRefOrFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#dataBlockValue}.
	 * @param ctx the parse tree
	 */
	void enterDataBlockValue(@NotNull SparqlParser.DataBlockValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#dataBlockValue}.
	 * @param ctx the parse tree
	 */
	void exitDataBlockValue(@NotNull SparqlParser.DataBlockValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relationalSetExpression}
	 * labeled alternative in {@link SparqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalSetExpression(@NotNull SparqlParser.RelationalSetExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relationalSetExpression}
	 * labeled alternative in {@link SparqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalSetExpression(@NotNull SparqlParser.RelationalSetExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#graphNode}.
	 * @param ctx the parse tree
	 */
	void enterGraphNode(@NotNull SparqlParser.GraphNodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#graphNode}.
	 * @param ctx the parse tree
	 */
	void exitGraphNode(@NotNull SparqlParser.GraphNodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#graphRefAll}.
	 * @param ctx the parse tree
	 */
	void enterGraphRefAll(@NotNull SparqlParser.GraphRefAllContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#graphRefAll}.
	 * @param ctx the parse tree
	 */
	void exitGraphRefAll(@NotNull SparqlParser.GraphRefAllContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#integer}.
	 * @param ctx the parse tree
	 */
	void enterInteger(@NotNull SparqlParser.IntegerContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#integer}.
	 * @param ctx the parse tree
	 */
	void exitInteger(@NotNull SparqlParser.IntegerContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#inlineDataFull}.
	 * @param ctx the parse tree
	 */
	void enterInlineDataFull(@NotNull SparqlParser.InlineDataFullContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#inlineDataFull}.
	 * @param ctx the parse tree
	 */
	void exitInlineDataFull(@NotNull SparqlParser.InlineDataFullContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(@NotNull SparqlParser.BooleanLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteral(@NotNull SparqlParser.BooleanLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#numericLiteralUnsigned}.
	 * @param ctx the parse tree
	 */
	void enterNumericLiteralUnsigned(@NotNull SparqlParser.NumericLiteralUnsignedContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#numericLiteralUnsigned}.
	 * @param ctx the parse tree
	 */
	void exitNumericLiteralUnsigned(@NotNull SparqlParser.NumericLiteralUnsignedContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unarySignedLiteralExpression}
	 * labeled alternative in {@link SparqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnarySignedLiteralExpression(@NotNull SparqlParser.UnarySignedLiteralExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unarySignedLiteralExpression}
	 * labeled alternative in {@link SparqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnarySignedLiteralExpression(@NotNull SparqlParser.UnarySignedLiteralExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#offsetClause}.
	 * @param ctx the parse tree
	 */
	void enterOffsetClause(@NotNull SparqlParser.OffsetClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#offsetClause}.
	 * @param ctx the parse tree
	 */
	void exitOffsetClause(@NotNull SparqlParser.OffsetClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#pathElt}.
	 * @param ctx the parse tree
	 */
	void enterPathElt(@NotNull SparqlParser.PathEltContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#pathElt}.
	 * @param ctx the parse tree
	 */
	void exitPathElt(@NotNull SparqlParser.PathEltContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#inlineDataOneVar}.
	 * @param ctx the parse tree
	 */
	void enterInlineDataOneVar(@NotNull SparqlParser.InlineDataOneVarContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#inlineDataOneVar}.
	 * @param ctx the parse tree
	 */
	void exitInlineDataOneVar(@NotNull SparqlParser.InlineDataOneVarContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#askQuery}.
	 * @param ctx the parse tree
	 */
	void enterAskQuery(@NotNull SparqlParser.AskQueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#askQuery}.
	 * @param ctx the parse tree
	 */
	void exitAskQuery(@NotNull SparqlParser.AskQueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#notExistsFunction}.
	 * @param ctx the parse tree
	 */
	void enterNotExistsFunction(@NotNull SparqlParser.NotExistsFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#notExistsFunction}.
	 * @param ctx the parse tree
	 */
	void exitNotExistsFunction(@NotNull SparqlParser.NotExistsFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(@NotNull SparqlParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#string}.
	 * @param ctx the parse tree
	 */
	void exitString(@NotNull SparqlParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#blankNodePropertyListPath}.
	 * @param ctx the parse tree
	 */
	void enterBlankNodePropertyListPath(@NotNull SparqlParser.BlankNodePropertyListPathContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#blankNodePropertyListPath}.
	 * @param ctx the parse tree
	 */
	void exitBlankNodePropertyListPath(@NotNull SparqlParser.BlankNodePropertyListPathContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#minusGraphPattern}.
	 * @param ctx the parse tree
	 */
	void enterMinusGraphPattern(@NotNull SparqlParser.MinusGraphPatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#minusGraphPattern}.
	 * @param ctx the parse tree
	 */
	void exitMinusGraphPattern(@NotNull SparqlParser.MinusGraphPatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#existsFunction}.
	 * @param ctx the parse tree
	 */
	void enterExistsFunction(@NotNull SparqlParser.ExistsFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#existsFunction}.
	 * @param ctx the parse tree
	 */
	void exitExistsFunction(@NotNull SparqlParser.ExistsFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void enterWhereClause(@NotNull SparqlParser.WhereClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void exitWhereClause(@NotNull SparqlParser.WhereClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#graphGraphPattern}.
	 * @param ctx the parse tree
	 */
	void enterGraphGraphPattern(@NotNull SparqlParser.GraphGraphPatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#graphGraphPattern}.
	 * @param ctx the parse tree
	 */
	void exitGraphGraphPattern(@NotNull SparqlParser.GraphGraphPatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#limitClause}.
	 * @param ctx the parse tree
	 */
	void enterLimitClause(@NotNull SparqlParser.LimitClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#limitClause}.
	 * @param ctx the parse tree
	 */
	void exitLimitClause(@NotNull SparqlParser.LimitClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#propertyListNotEmpty}.
	 * @param ctx the parse tree
	 */
	void enterPropertyListNotEmpty(@NotNull SparqlParser.PropertyListNotEmptyContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#propertyListNotEmpty}.
	 * @param ctx the parse tree
	 */
	void exitPropertyListNotEmpty(@NotNull SparqlParser.PropertyListNotEmptyContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(@NotNull SparqlParser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(@NotNull SparqlParser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#drop}.
	 * @param ctx the parse tree
	 */
	void enterDrop(@NotNull SparqlParser.DropContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#drop}.
	 * @param ctx the parse tree
	 */
	void exitDrop(@NotNull SparqlParser.DropContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#baseDecl}.
	 * @param ctx the parse tree
	 */
	void enterBaseDecl(@NotNull SparqlParser.BaseDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#baseDecl}.
	 * @param ctx the parse tree
	 */
	void exitBaseDecl(@NotNull SparqlParser.BaseDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#propertyList}.
	 * @param ctx the parse tree
	 */
	void enterPropertyList(@NotNull SparqlParser.PropertyListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#propertyList}.
	 * @param ctx the parse tree
	 */
	void exitPropertyList(@NotNull SparqlParser.PropertyListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#insertClause}.
	 * @param ctx the parse tree
	 */
	void enterInsertClause(@NotNull SparqlParser.InsertClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#insertClause}.
	 * @param ctx the parse tree
	 */
	void exitInsertClause(@NotNull SparqlParser.InsertClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpression(@NotNull SparqlParser.PrimaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpression(@NotNull SparqlParser.PrimaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryNegationExpression}
	 * labeled alternative in {@link SparqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryNegationExpression(@NotNull SparqlParser.UnaryNegationExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryNegationExpression}
	 * labeled alternative in {@link SparqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryNegationExpression(@NotNull SparqlParser.UnaryNegationExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#insertData}.
	 * @param ctx the parse tree
	 */
	void enterInsertData(@NotNull SparqlParser.InsertDataContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#insertData}.
	 * @param ctx the parse tree
	 */
	void exitInsertData(@NotNull SparqlParser.InsertDataContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#pathNegatedPropertySet}.
	 * @param ctx the parse tree
	 */
	void enterPathNegatedPropertySet(@NotNull SparqlParser.PathNegatedPropertySetContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#pathNegatedPropertySet}.
	 * @param ctx the parse tree
	 */
	void exitPathNegatedPropertySet(@NotNull SparqlParser.PathNegatedPropertySetContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#selectClause}.
	 * @param ctx the parse tree
	 */
	void enterSelectClause(@NotNull SparqlParser.SelectClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#selectClause}.
	 * @param ctx the parse tree
	 */
	void exitSelectClause(@NotNull SparqlParser.SelectClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#objectListPath}.
	 * @param ctx the parse tree
	 */
	void enterObjectListPath(@NotNull SparqlParser.ObjectListPathContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#objectListPath}.
	 * @param ctx the parse tree
	 */
	void exitObjectListPath(@NotNull SparqlParser.ObjectListPathContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#subStringExpression}.
	 * @param ctx the parse tree
	 */
	void enterSubStringExpression(@NotNull SparqlParser.SubStringExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#subStringExpression}.
	 * @param ctx the parse tree
	 */
	void exitSubStringExpression(@NotNull SparqlParser.SubStringExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(@NotNull SparqlParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(@NotNull SparqlParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#anon}.
	 * @param ctx the parse tree
	 */
	void enterAnon(@NotNull SparqlParser.AnonContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#anon}.
	 * @param ctx the parse tree
	 */
	void exitAnon(@NotNull SparqlParser.AnonContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#triplesSameSubjectPath}.
	 * @param ctx the parse tree
	 */
	void enterTriplesSameSubjectPath(@NotNull SparqlParser.TriplesSameSubjectPathContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#triplesSameSubjectPath}.
	 * @param ctx the parse tree
	 */
	void exitTriplesSameSubjectPath(@NotNull SparqlParser.TriplesSameSubjectPathContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryAdditiveExpression}
	 * labeled alternative in {@link SparqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryAdditiveExpression(@NotNull SparqlParser.UnaryAdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryAdditiveExpression}
	 * labeled alternative in {@link SparqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryAdditiveExpression(@NotNull SparqlParser.UnaryAdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#verbSimple}.
	 * @param ctx the parse tree
	 */
	void enterVerbSimple(@NotNull SparqlParser.VerbSimpleContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#verbSimple}.
	 * @param ctx the parse tree
	 */
	void exitVerbSimple(@NotNull SparqlParser.VerbSimpleContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#describeQuery}.
	 * @param ctx the parse tree
	 */
	void enterDescribeQuery(@NotNull SparqlParser.DescribeQueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#describeQuery}.
	 * @param ctx the parse tree
	 */
	void exitDescribeQuery(@NotNull SparqlParser.DescribeQueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#triplesSameSubject}.
	 * @param ctx the parse tree
	 */
	void enterTriplesSameSubject(@NotNull SparqlParser.TriplesSameSubjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#triplesSameSubject}.
	 * @param ctx the parse tree
	 */
	void exitTriplesSameSubject(@NotNull SparqlParser.TriplesSameSubjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#prologue}.
	 * @param ctx the parse tree
	 */
	void enterPrologue(@NotNull SparqlParser.PrologueContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#prologue}.
	 * @param ctx the parse tree
	 */
	void exitPrologue(@NotNull SparqlParser.PrologueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code conditionalAndExpression}
	 * labeled alternative in {@link SparqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterConditionalAndExpression(@NotNull SparqlParser.ConditionalAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code conditionalAndExpression}
	 * labeled alternative in {@link SparqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitConditionalAndExpression(@NotNull SparqlParser.ConditionalAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#numericLiteralNegative}.
	 * @param ctx the parse tree
	 */
	void enterNumericLiteralNegative(@NotNull SparqlParser.NumericLiteralNegativeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#numericLiteralNegative}.
	 * @param ctx the parse tree
	 */
	void exitNumericLiteralNegative(@NotNull SparqlParser.NumericLiteralNegativeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code additiveExpression}
	 * labeled alternative in {@link SparqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(@NotNull SparqlParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code additiveExpression}
	 * labeled alternative in {@link SparqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(@NotNull SparqlParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#groupCondition}.
	 * @param ctx the parse tree
	 */
	void enterGroupCondition(@NotNull SparqlParser.GroupConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#groupCondition}.
	 * @param ctx the parse tree
	 */
	void exitGroupCondition(@NotNull SparqlParser.GroupConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#quads}.
	 * @param ctx the parse tree
	 */
	void enterQuads(@NotNull SparqlParser.QuadsContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#quads}.
	 * @param ctx the parse tree
	 */
	void exitQuads(@NotNull SparqlParser.QuadsContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#dataBlock}.
	 * @param ctx the parse tree
	 */
	void enterDataBlock(@NotNull SparqlParser.DataBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#dataBlock}.
	 * @param ctx the parse tree
	 */
	void exitDataBlock(@NotNull SparqlParser.DataBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#blankNode}.
	 * @param ctx the parse tree
	 */
	void enterBlankNode(@NotNull SparqlParser.BlankNodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#blankNode}.
	 * @param ctx the parse tree
	 */
	void exitBlankNode(@NotNull SparqlParser.BlankNodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#triplesNodePath}.
	 * @param ctx the parse tree
	 */
	void enterTriplesNodePath(@NotNull SparqlParser.TriplesNodePathContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#triplesNodePath}.
	 * @param ctx the parse tree
	 */
	void exitTriplesNodePath(@NotNull SparqlParser.TriplesNodePathContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#propertyListPath}.
	 * @param ctx the parse tree
	 */
	void enterPropertyListPath(@NotNull SparqlParser.PropertyListPathContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#propertyListPath}.
	 * @param ctx the parse tree
	 */
	void exitPropertyListPath(@NotNull SparqlParser.PropertyListPathContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#groupClause}.
	 * @param ctx the parse tree
	 */
	void enterGroupClause(@NotNull SparqlParser.GroupClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#groupClause}.
	 * @param ctx the parse tree
	 */
	void exitGroupClause(@NotNull SparqlParser.GroupClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#dataBlockValues}.
	 * @param ctx the parse tree
	 */
	void enterDataBlockValues(@NotNull SparqlParser.DataBlockValuesContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#dataBlockValues}.
	 * @param ctx the parse tree
	 */
	void exitDataBlockValues(@NotNull SparqlParser.DataBlockValuesContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#constructTemplate}.
	 * @param ctx the parse tree
	 */
	void enterConstructTemplate(@NotNull SparqlParser.ConstructTemplateContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#constructTemplate}.
	 * @param ctx the parse tree
	 */
	void exitConstructTemplate(@NotNull SparqlParser.ConstructTemplateContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#filter}.
	 * @param ctx the parse tree
	 */
	void enterFilter(@NotNull SparqlParser.FilterContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#filter}.
	 * @param ctx the parse tree
	 */
	void exitFilter(@NotNull SparqlParser.FilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multiplicativeExpression}
	 * labeled alternative in {@link SparqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(@NotNull SparqlParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiplicativeExpression}
	 * labeled alternative in {@link SparqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(@NotNull SparqlParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#update}.
	 * @param ctx the parse tree
	 */
	void enterUpdate(@NotNull SparqlParser.UpdateContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#update}.
	 * @param ctx the parse tree
	 */
	void exitUpdate(@NotNull SparqlParser.UpdateContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#varOrTerm}.
	 * @param ctx the parse tree
	 */
	void enterVarOrTerm(@NotNull SparqlParser.VarOrTermContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#varOrTerm}.
	 * @param ctx the parse tree
	 */
	void exitVarOrTerm(@NotNull SparqlParser.VarOrTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#var}.
	 * @param ctx the parse tree
	 */
	void enterVar(@NotNull SparqlParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#var}.
	 * @param ctx the parse tree
	 */
	void exitVar(@NotNull SparqlParser.VarContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#deleteWhere}.
	 * @param ctx the parse tree
	 */
	void enterDeleteWhere(@NotNull SparqlParser.DeleteWhereContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#deleteWhere}.
	 * @param ctx the parse tree
	 */
	void exitDeleteWhere(@NotNull SparqlParser.DeleteWhereContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#regexExpression}.
	 * @param ctx the parse tree
	 */
	void enterRegexExpression(@NotNull SparqlParser.RegexExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#regexExpression}.
	 * @param ctx the parse tree
	 */
	void exitRegexExpression(@NotNull SparqlParser.RegexExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#triplesNode}.
	 * @param ctx the parse tree
	 */
	void enterTriplesNode(@NotNull SparqlParser.TriplesNodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#triplesNode}.
	 * @param ctx the parse tree
	 */
	void exitTriplesNode(@NotNull SparqlParser.TriplesNodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#pathPrimary}.
	 * @param ctx the parse tree
	 */
	void enterPathPrimary(@NotNull SparqlParser.PathPrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#pathPrimary}.
	 * @param ctx the parse tree
	 */
	void exitPathPrimary(@NotNull SparqlParser.PathPrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#objectList}.
	 * @param ctx the parse tree
	 */
	void enterObjectList(@NotNull SparqlParser.ObjectListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#objectList}.
	 * @param ctx the parse tree
	 */
	void exitObjectList(@NotNull SparqlParser.ObjectListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#object}.
	 * @param ctx the parse tree
	 */
	void enterObject(@NotNull SparqlParser.ObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#object}.
	 * @param ctx the parse tree
	 */
	void exitObject(@NotNull SparqlParser.ObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#add}.
	 * @param ctx the parse tree
	 */
	void enterAdd(@NotNull SparqlParser.AddContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#add}.
	 * @param ctx the parse tree
	 */
	void exitAdd(@NotNull SparqlParser.AddContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#iri}.
	 * @param ctx the parse tree
	 */
	void enterIri(@NotNull SparqlParser.IriContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#iri}.
	 * @param ctx the parse tree
	 */
	void exitIri(@NotNull SparqlParser.IriContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#pathSequence}.
	 * @param ctx the parse tree
	 */
	void enterPathSequence(@NotNull SparqlParser.PathSequenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#pathSequence}.
	 * @param ctx the parse tree
	 */
	void exitPathSequence(@NotNull SparqlParser.PathSequenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#groupGraphPatternSubList}.
	 * @param ctx the parse tree
	 */
	void enterGroupGraphPatternSubList(@NotNull SparqlParser.GroupGraphPatternSubListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#groupGraphPatternSubList}.
	 * @param ctx the parse tree
	 */
	void exitGroupGraphPatternSubList(@NotNull SparqlParser.GroupGraphPatternSubListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#pathEltOrInverse}.
	 * @param ctx the parse tree
	 */
	void enterPathEltOrInverse(@NotNull SparqlParser.PathEltOrInverseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#pathEltOrInverse}.
	 * @param ctx the parse tree
	 */
	void exitPathEltOrInverse(@NotNull SparqlParser.PathEltOrInverseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#verb}.
	 * @param ctx the parse tree
	 */
	void enterVerb(@NotNull SparqlParser.VerbContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#verb}.
	 * @param ctx the parse tree
	 */
	void exitVerb(@NotNull SparqlParser.VerbContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#optionalGraphPattern}.
	 * @param ctx the parse tree
	 */
	void enterOptionalGraphPattern(@NotNull SparqlParser.OptionalGraphPatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#optionalGraphPattern}.
	 * @param ctx the parse tree
	 */
	void exitOptionalGraphPattern(@NotNull SparqlParser.OptionalGraphPatternContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relationalExpression}
	 * labeled alternative in {@link SparqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpression(@NotNull SparqlParser.RelationalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relationalExpression}
	 * labeled alternative in {@link SparqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpression(@NotNull SparqlParser.RelationalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#valuesClause}.
	 * @param ctx the parse tree
	 */
	void enterValuesClause(@NotNull SparqlParser.ValuesClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#valuesClause}.
	 * @param ctx the parse tree
	 */
	void exitValuesClause(@NotNull SparqlParser.ValuesClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#blankNodePropertyList}.
	 * @param ctx the parse tree
	 */
	void enterBlankNodePropertyList(@NotNull SparqlParser.BlankNodePropertyListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#blankNodePropertyList}.
	 * @param ctx the parse tree
	 */
	void exitBlankNodePropertyList(@NotNull SparqlParser.BlankNodePropertyListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#path}.
	 * @param ctx the parse tree
	 */
	void enterPath(@NotNull SparqlParser.PathContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#path}.
	 * @param ctx the parse tree
	 */
	void exitPath(@NotNull SparqlParser.PathContext ctx);
	/**
	 * Enter a parse tree produced by the {@code conditionalOrExpression}
	 * labeled alternative in {@link SparqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterConditionalOrExpression(@NotNull SparqlParser.ConditionalOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code conditionalOrExpression}
	 * labeled alternative in {@link SparqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitConditionalOrExpression(@NotNull SparqlParser.ConditionalOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#datasetClause}.
	 * @param ctx the parse tree
	 */
	void enterDatasetClause(@NotNull SparqlParser.DatasetClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#datasetClause}.
	 * @param ctx the parse tree
	 */
	void exitDatasetClause(@NotNull SparqlParser.DatasetClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#deleteData}.
	 * @param ctx the parse tree
	 */
	void enterDeleteData(@NotNull SparqlParser.DeleteDataContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#deleteData}.
	 * @param ctx the parse tree
	 */
	void exitDeleteData(@NotNull SparqlParser.DeleteDataContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#modify}.
	 * @param ctx the parse tree
	 */
	void enterModify(@NotNull SparqlParser.ModifyContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#modify}.
	 * @param ctx the parse tree
	 */
	void exitModify(@NotNull SparqlParser.ModifyContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#graphPatternNotTriples}.
	 * @param ctx the parse tree
	 */
	void enterGraphPatternNotTriples(@NotNull SparqlParser.GraphPatternNotTriplesContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#graphPatternNotTriples}.
	 * @param ctx the parse tree
	 */
	void exitGraphPatternNotTriples(@NotNull SparqlParser.GraphPatternNotTriplesContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#unaryLiteralExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryLiteralExpression(@NotNull SparqlParser.UnaryLiteralExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#unaryLiteralExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryLiteralExpression(@NotNull SparqlParser.UnaryLiteralExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#propertyListPathNotEmptyList}.
	 * @param ctx the parse tree
	 */
	void enterPropertyListPathNotEmptyList(@NotNull SparqlParser.PropertyListPathNotEmptyListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#propertyListPathNotEmptyList}.
	 * @param ctx the parse tree
	 */
	void exitPropertyListPathNotEmptyList(@NotNull SparqlParser.PropertyListPathNotEmptyListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#pathOneInPropertySet}.
	 * @param ctx the parse tree
	 */
	void enterPathOneInPropertySet(@NotNull SparqlParser.PathOneInPropertySetContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#pathOneInPropertySet}.
	 * @param ctx the parse tree
	 */
	void exitPathOneInPropertySet(@NotNull SparqlParser.PathOneInPropertySetContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#pathAlternative}.
	 * @param ctx the parse tree
	 */
	void enterPathAlternative(@NotNull SparqlParser.PathAlternativeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#pathAlternative}.
	 * @param ctx the parse tree
	 */
	void exitPathAlternative(@NotNull SparqlParser.PathAlternativeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#bind}.
	 * @param ctx the parse tree
	 */
	void enterBind(@NotNull SparqlParser.BindContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#bind}.
	 * @param ctx the parse tree
	 */
	void exitBind(@NotNull SparqlParser.BindContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#propertyListPathNotEmpty}.
	 * @param ctx the parse tree
	 */
	void enterPropertyListPathNotEmpty(@NotNull SparqlParser.PropertyListPathNotEmptyContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#propertyListPathNotEmpty}.
	 * @param ctx the parse tree
	 */
	void exitPropertyListPathNotEmpty(@NotNull SparqlParser.PropertyListPathNotEmptyContext ctx);
	/**
	 * Enter a parse tree produced by {@link SparqlParser#nil}.
	 * @param ctx the parse tree
	 */
	void enterNil(@NotNull SparqlParser.NilContext ctx);
	/**
	 * Exit a parse tree produced by {@link SparqlParser#nil}.
	 * @param ctx the parse tree
	 */
	void exitNil(@NotNull SparqlParser.NilContext ctx);
}