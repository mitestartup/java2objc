/*
 * Copyright (C) 2009 Inderjeet Singh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.java2objc.converters;

import japa.parser.ast.expr.BinaryExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.StringLiteralExpr;

import java.util.LinkedList;
import java.util.List;

import com.googlecode.java2objc.code.ObjcExpressionBinary;
import com.googlecode.java2objc.code.ObjcExpression;
import com.googlecode.java2objc.code.ObjcExpressionMethodCall;
import com.googlecode.java2objc.code.ObjcExpressionMethodCallString;
import com.googlecode.java2objc.code.ObjcExpressionStringLiteral;
import com.googlecode.java2objc.objc.CompilationContext;

/**
 * Converts Java expressions into Objective C expressions
 * 
 * @author Inderjeet Singh
 */
public final class ExpressionConverter {

  private final CompilationContext context;

  /**
   * @param compilationContext
   */
  public ExpressionConverter(CompilationContext context) {
    this.context = context;
  }

  public List<ObjcExpression> to(List<Expression> expressions) {
    List<ObjcExpression> objcExpressions = new LinkedList<ObjcExpression>();
    if (expressions != null) {
      for (Expression expr : expressions) {
        objcExpressions.add(to(expr));
      }
    }
    return objcExpressions;
  }

  public ObjcExpression to(Expression expr) {
    if (expr instanceof StringLiteralExpr) {
      return new ObjcExpressionStringLiteral(expr);
    } else if (expr instanceof MethodCallExpr) {
      MethodCallExpr callExpr = (MethodCallExpr)expr;
      if (isStringMethodCall(callExpr)) {
        return new ObjcExpressionMethodCallString(context, callExpr);         
      } else {
        return new ObjcExpressionMethodCall(context, callExpr);
      }
    } else if (expr instanceof BinaryExpr) {
      return new ObjcExpressionBinary(context, (BinaryExpr) expr);
    } else {
      // TODO (inder): bring in real expression conversion
      return new ObjcExpression(expr.toString());
    }
  }

  /**
   * @param callExpr
   * @return
   */
  private boolean isStringMethodCall(MethodCallExpr callExpr) {
    Expression scope = callExpr.getScope();
    return scope != null && scope.toString().equals("String");
  }
}
