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
package com.googlecode.java2objc.objc;

import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.Statement;

import java.util.LinkedList;
import java.util.List;


public class ObjcStatementBlock extends ObjcStatement {
  
  public static class Builder {
    private final List<ObjcStatement> stmts;
    public Builder() {
      stmts = new LinkedList<ObjcStatement>();
    }
    public Builder addStatement(ObjcStatement stmt) {
      stmts.add(stmt);
      return this;
    }
    public ObjcStatementBlock build() {
      return new ObjcStatementBlock(stmts);
    }
  }

  private final List<ObjcStatement> stmts;
  
  public ObjcStatementBlock(BlockStmt block) {
    stmts = new LinkedList<ObjcStatement>();
    for (Statement stmt : block.getStmts()) {
      stmts.add(StatementConverter.to(stmt));
    }    
  }

  private ObjcStatementBlock(List<ObjcStatement> stmts) {
    this.stmts = stmts;
  }
  
  @Override
  public void append(SourceCodeWriter writer) {
    writer.append("{");
    writer.indent();
    for (ObjcStatement stmt : stmts) {
      writer.append(stmt);
    }
    writer.unIndent();
    writer.appendLine("}");
  }
}