xlecho编辑整理，欢迎转载，转载请声明文章来源。欢迎添加echo微信(微信号：t2421499075)交流学习。 百战不败，依不自称常胜，百败不颓，依能奋力前行。——这才是真正的堪称强大！！

---

<div class="section">
<h2><a name="XML_.E6.98.A0.E5.B0.84.E6.96.87.E4.BB.B6"></a>XML 映射文件</h2>
      
<p>MyBatis 的真正强大在于它的映射语句，这是它的魔力所在。由于它的异常强大，映射器的
      XML 文件就显得相对简单。如果拿它跟具有相同功能的 JDBC 代码进行对比，你会立即发现省掉了将近
      95% 的代码。MyBatis 为聚焦于 SQL 而构建，以尽可能地为你减少麻烦。</p>
      
<p>SQL 映射文件只有很少的几个顶级元素（按照应被定义的顺序列出）：</p>
      
<ul>
        
<li>
          <tt>cache</tt>
          – 对给定命名空间的缓存配置。
        </li>
        
<li>
          <tt>cache-ref</tt>
          – 对其他命名空间缓存配置的引用。
        </li>
        
<li>
          <tt>resultMap</tt>
          – 是最复杂也是最强大的元素，用来描述如何从数据库结果集中来加载对象。
        </li>
        
<li>
          <s>
            </s><tt><s>parameterMap</s></tt><s>
            – 已被废弃！老式风格的参数映射。更好的办法是使用内联参数，此元素可能在将来被移除。文档中不会介绍此元素。
          </s>
        </li>
        
<li>
          <tt>sql</tt>
          – 可被其他语句引用的可重用语句块。
        </li>
        
<li>
          <tt>insert</tt>
          – 映射插入语句
        </li>
        
<li>
          <tt>update</tt>
          – 映射更新语句
        </li>
        
<li>
          <tt>delete</tt>
          – 映射删除语句
        </li>
        
<li>
          <tt>select</tt>
          – 映射查询语句
        </li>
      </ul>
      
<p>下一部分将从语句本身开始来描述每个元素的细节。</p>

      <a name="select"></a>
<div class="section" id="select">
<h3><a name="select"></a>select</h3>
        
<p>查询语句是 MyBatis 中最常用的元素之一，光能把数据存到数据库中价值并不大，只有还能重新取出来才有用，多数应用也都是查询比修改要频繁。对每个插入、更新或删除操作，通常间隔多个查询操作。这是 MyBatis 的基本原则之一，也是将焦点和努力放在查询和结果映射的原因。简单查询的 select 元素是非常简单的。比如：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;select</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"selectPerson"</span><span class="pln"> </span><span class="atn">parameterType</span><span class="pun">=</span><span class="atv">"int"</span><span class="pln"> </span><span class="atn">resultType</span><span class="pun">=</span><span class="atv">"hashmap"</span><span class="tag">&gt;</span><span class="pln">
  SELECT * FROM PERSON WHERE ID = #{id}
</span><span class="tag">&lt;/select&gt;</span></pre></div>

        
<p>
          这个语句被称作 selectPerson，接受一个 int（或 Integer）类型的参数，并返回一个
          HashMap 类型的对象，其中的键是列名，值便是结果行中的对应值。
        </p>

        
<p>注意参数符号：</p>

        
<div class="source"><pre class="prettyprint"><span class="com">#{id}</span></pre></div>

        
<p>
          这就告诉 MyBatis 创建一个预处理语句（PreparedStatement）参数，在 JDBC
          中，这样的一个参数在 SQL 中会由一个“?”来标识，并被传递到一个新的预处理语句中，就像这样：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="com">// 近似的 JDBC 代码，非 MyBatis 代码...</span><span class="pln">
</span><span class="typ">String</span><span class="pln"> selectPerson </span><span class="pun">=</span><span class="pln"> </span><span class="str">"SELECT * FROM PERSON WHERE ID=?"</span><span class="pun">;</span><span class="pln">
</span><span class="typ">PreparedStatement</span><span class="pln"> ps </span><span class="pun">=</span><span class="pln"> conn</span><span class="pun">.</span><span class="pln">prepareStatement</span><span class="pun">(</span><span class="pln">selectPerson</span><span class="pun">);</span><span class="pln">
ps</span><span class="pun">.</span><span class="pln">setInt</span><span class="pun">(</span><span class="lit">1</span><span class="pun">,</span><span class="pln">id</span><span class="pun">);</span></pre></div>

        
<p>
          当然，使用 JDBC 意味着需要更多的代码来提取结果并将它们映射到对象实例中，而这就是
          MyBatis 节省你时间的地方。参数和结果映射还有更深入的细节。这些细节会分别在后面单独的小节中呈现。
        </p>

        
<p>
          select 元素允许你配置很多属性来配置每条语句的作用细节。
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;select</span><span class="pln">
  </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"selectPerson"</span><span class="pln">
  </span><span class="atn">parameterType</span><span class="pun">=</span><span class="atv">"int"</span><span class="pln">
  </span><span class="atn">parameterMap</span><span class="pun">=</span><span class="atv">"deprecated"</span><span class="pln">
  </span><span class="atn">resultType</span><span class="pun">=</span><span class="atv">"hashmap"</span><span class="pln">
  </span><span class="atn">resultMap</span><span class="pun">=</span><span class="atv">"personResultMap"</span><span class="pln">
  </span><span class="atn">flushCache</span><span class="pun">=</span><span class="atv">"false"</span><span class="pln">
  </span><span class="atn">useCache</span><span class="pun">=</span><span class="atv">"true"</span><span class="pln">
  </span><span class="atn">timeout</span><span class="pun">=</span><span class="atv">"10"</span><span class="pln">
  </span><span class="atn">fetchSize</span><span class="pun">=</span><span class="atv">"256"</span><span class="pln">
  </span><span class="atn">statementType</span><span class="pun">=</span><span class="atv">"PREPARED"</span><span class="pln">
  </span><span class="atn">resultSetType</span><span class="pun">=</span><span class="atv">"FORWARD_ONLY"</span><span class="tag">&gt;</span></pre></div>

        
<table border="0" class="table table-striped"><caption>Select 元素的属性</caption>
          
          <thead>
            
<tr class="a">
              
<th>属性</th>
              
<th>描述</th>
            </tr>
          </thead>
          <tbody>
            
<tr class="b">
              
<td><tt>id</tt></td>
              
<td>
              在命名空间中唯一的标识符，可以被用来引用这条语句。
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>parameterType</tt></td>
              
<td>
                将会传入这条语句的参数类的完全限定名或别名。这个属性是可选的，因为
                MyBatis 可以通过类型处理器（TypeHandler） 推断出具体传入语句的参数，默认值为未设置（unset）。
              </td>
            </tr>
            
<tr class="b">
              
<td>
                <s>parameterMap</s>
              </td>
              
<td>
                <s>
                  这是引用外部 parameterMap 的已经被废弃的方法。请使用内联参数映射和 parameterType 属性。
                </s>
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>resultType</tt></td>
              
<td>
                从这条语句中返回的期望类型的类的完全限定名或别名。
                注意如果返回的是集合，那应该设置为集合包含的类型，而不是集合本身。可以使用
                resultType 或 resultMap，但不能同时使用。
              </td>
            </tr>
            
<tr class="b">
              
<td><tt>resultMap</tt></td>
              
<td>
                外部 resultMap 的命名引用。结果集的映射是 MyBatis
                最强大的特性，如果你对其理解透彻，许多复杂映射的情形都能迎刃而解。可以使用
                resultMap 或 resultType，但不能同时使用。
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>flushCache</tt></td>
              
<td>
                将其设置为 true 后，只要语句被调用，都会导致本地缓存和二级缓存被清空，默认值：false。
              </td>
            </tr>
            
<tr class="b">
              
<td><tt>useCache</tt></td>
              
<td>
                将其设置为 true 后，将会导致本条语句的结果被二级缓存缓存起来，默认值：对 select 元素为 true。
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>timeout</tt></td>
              
<td>
                这个设置是在抛出异常之前，驱动程序等待数据库返回请求结果的秒数。默认值为未设置（unset）（依赖驱动）。
              </td>
            </tr>
            
<tr class="b">
              
<td><tt>fetchSize</tt></td>
              
<td>
                这是一个给驱动的提示，尝试让驱动程序每次批量返回的结果行数和这个设置值相等。
                默认值为未设置（unset）（依赖驱动）。
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>statementType</tt></td>
              
<td>
                STATEMENT，PREPARED 或 CALLABLE 中的一个。这会让 MyBatis 分别使用
                Statement，PreparedStatement 或 CallableStatement，默认值：PREPARED。
              </td>
            </tr>
            
<tr class="b">
              
<td><tt>resultSetType</tt></td>
              
<td>
                FORWARD_ONLY，SCROLL_SENSITIVE, SCROLL_INSENSITIVE 或
                DEFAULT（等价于 unset） 中的一个，默认值为 unset （依赖驱动）。
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>databaseId</tt></td>
              
<td>
                如果配置了数据库厂商标识（databaseIdProvider），MyBatis
                会加载所有的不带 databaseId 或匹配当前 databaseId 的语句；如果带或者不带的语句都有，则不带的会被忽略。
              </td>
            </tr>
            
<tr class="b">
              
<td><tt>resultOrdered</tt></td>
              
<td>
                这个设置仅针对嵌套结果 select 语句适用：如果为
                true，就是假设包含了嵌套结果集或是分组，这样的话当返回一个主结果行的时候，就不会发生有对前面结果集的引用的情况。
                这就使得在获取嵌套的结果集的时候不至于导致内存不够用。默认值：<tt>false</tt>。
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>resultSets</tt></td>
              
<td>
                这个设置仅对多结果集的情况适用。它将列出语句执行后返回的结果集并给每个结果集一个名称，名称是逗号分隔的。
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <a name="insert_update_and_delete"></a>
<div class="section" id="insert_update_and_delete">
<h3><a name="insert.2C_update_.E5.92.8C_delete"></a>insert, update 和 delete</h3>
        
<p>
          数据变更语句 insert，update 和 delete 的实现非常接近：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;insert</span><span class="pln">
  </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"insertAuthor"</span><span class="pln">
  </span><span class="atn">parameterType</span><span class="pun">=</span><span class="atv">"domain.blog.Author"</span><span class="pln">
  </span><span class="atn">flushCache</span><span class="pun">=</span><span class="atv">"true"</span><span class="pln">
  </span><span class="atn">statementType</span><span class="pun">=</span><span class="atv">"PREPARED"</span><span class="pln">
  </span><span class="atn">keyProperty</span><span class="pun">=</span><span class="atv">""</span><span class="pln">
  </span><span class="atn">keyColumn</span><span class="pun">=</span><span class="atv">""</span><span class="pln">
  </span><span class="atn">useGeneratedKeys</span><span class="pun">=</span><span class="atv">""</span><span class="pln">
  </span><span class="atn">timeout</span><span class="pun">=</span><span class="atv">"20"</span><span class="tag">&gt;</span><span class="pln">

</span><span class="tag">&lt;update</span><span class="pln">
  </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"updateAuthor"</span><span class="pln">
  </span><span class="atn">parameterType</span><span class="pun">=</span><span class="atv">"domain.blog.Author"</span><span class="pln">
  </span><span class="atn">flushCache</span><span class="pun">=</span><span class="atv">"true"</span><span class="pln">
  </span><span class="atn">statementType</span><span class="pun">=</span><span class="atv">"PREPARED"</span><span class="pln">
  </span><span class="atn">timeout</span><span class="pun">=</span><span class="atv">"20"</span><span class="tag">&gt;</span><span class="pln">

</span><span class="tag">&lt;delete</span><span class="pln">
  </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"deleteAuthor"</span><span class="pln">
  </span><span class="atn">parameterType</span><span class="pun">=</span><span class="atv">"domain.blog.Author"</span><span class="pln">
  </span><span class="atn">flushCache</span><span class="pun">=</span><span class="atv">"true"</span><span class="pln">
  </span><span class="atn">statementType</span><span class="pun">=</span><span class="atv">"PREPARED"</span><span class="pln">
  </span><span class="atn">timeout</span><span class="pun">=</span><span class="atv">"20"</span><span class="tag">&gt;</span></pre></div>

        
<table border="0" class="table table-striped"><caption>Insert, Update, Delete 元素的属性</caption>
          
          <thead>
            
<tr class="a">
              
<th>属性</th>
              
<th>描述</th>
            </tr>
          </thead>
          <tbody>
            
<tr class="b">
              
<td><tt>id</tt></td>
              
<td>命名空间中的唯一标识符，可被用来代表这条语句。</td>
            </tr>
            
<tr class="a">
              
<td><tt>parameterType</tt></td>
              
<td>
                将要传入语句的参数的完全限定类名或别名。这个属性是可选的，因为 MyBatis
                可以通过类型处理器推断出具体传入语句的参数，默认值为未设置（unset）。
              </td>
            </tr>
            
<tr class="b">
              
<td>
                <tt><s>parameterMap</s></tt>
              </td>
              
<td>
                <s>这是引用外部 parameterMap 的已经被废弃的方法。请使用内联参数映射和 parameterType 属性。
                </s>
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>flushCache</tt></td>
              
<td>将其设置为 true 后，只要语句被调用，都会导致本地缓存和二级缓存被清空，默认值：true（对于 insert、update 和 delete 语句）。
              </td>
            </tr>
            
<tr class="b">
              
<td><tt>timeout</tt></td>
              
<td>这个设置是在抛出异常之前，驱动程序等待数据库返回请求结果的秒数。默认值为未设置（unset）（依赖驱动）。
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>statementType</tt></td>
              
<td>
                STATEMENT，PREPARED 或 CALLABLE 的一个。这会让 MyBatis 分别使用
                Statement，PreparedStatement 或 CallableStatement，默认值：PREPARED。
              </td>
            </tr>
            
<tr class="b">
              
<td><tt>useGeneratedKeys</tt></td>
              
<td>
                （仅对 insert 和 update 有用）这会令 MyBatis 使用 JDBC 的
                getGeneratedKeys 方法来取出由数据库内部生成的主键（比如：像 MySQL 和 SQL Server 这样的关系数据库管理系统的自动递增字段），默认值：false。
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>keyProperty</tt></td>
              
<td>
               （仅对 insert 和 update 有用）唯一标记一个属性，MyBatis 会通过
                getGeneratedKeys 的返回值或者通过 insert 语句的 selectKey 子元素设置它的键值，默认值：未设置（<tt>unset</tt>）。如果希望得到多个生成的列，也可以是逗号分隔的属性名称列表。
              </td>
            </tr>
            
<tr class="b">
              
<td><tt>keyColumn</tt></td>
              
<td>
                （仅对 insert 和 update 有用）通过生成的键值设置表中的列名，这个设置仅在某些数据库（像 PostgreSQL）是必须的，当主键列不是表中的第一列的时候需要设置。如果希望使用多个生成的列，也可以设置为逗号分隔的属性名称列表。
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>databaseId</tt></td>
              
<td>
                如果配置了数据库厂商标识（databaseIdProvider），MyBatis 会加载所有的不带
                databaseId 或匹配当前 databaseId 的语句；如果带或者不带的语句都有，则不带的会被忽略。
              </td>
            </tr>
          </tbody>
        </table>

        
<p>下面就是 insert，update 和 delete 语句的示例：</p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;insert</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"insertAuthor"</span><span class="tag">&gt;</span><span class="pln">
  insert into Author (id,username,password,email,bio)
  values (#{id},#{username},#{password},#{email},#{bio})
</span><span class="tag">&lt;/insert&gt;</span><span class="pln">

</span><span class="tag">&lt;update</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"updateAuthor"</span><span class="tag">&gt;</span><span class="pln">
  update Author set
    username = #{username},
    password = #{password},
    email = #{email},
    bio = #{bio}
  where id = #{id}
</span><span class="tag">&lt;/update&gt;</span><span class="pln">

</span><span class="tag">&lt;delete</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"deleteAuthor"</span><span class="tag">&gt;</span><span class="pln">
  delete from Author where id = #{id}
</span><span class="tag">&lt;/delete&gt;</span></pre></div>

        
<p>如前所述，插入语句的配置规则更加丰富，在插入语句里面有一些额外的属性和子元素用来处理主键的生成，而且有多种生成方式。</p>

        
<p>首先，如果你的数据库支持自动生成主键的字段（比如 MySQL 和 SQL Server），那么你可以设置 useGeneratedKeys=”true”，然后再把 keyProperty 设置到目标属性上就 OK 了。例如，如果上面的 Author 表已经对 id 使用了自动生成的列类型，那么语句可以修改为：</p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;insert</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"insertAuthor"</span><span class="pln"> </span><span class="atn">useGeneratedKeys</span><span class="pun">=</span><span class="atv">"true"</span><span class="pln">
    </span><span class="atn">keyProperty</span><span class="pun">=</span><span class="atv">"id"</span><span class="tag">&gt;</span><span class="pln">
  insert into Author (username,password,email,bio)
  values (#{username},#{password},#{email},#{bio})
</span><span class="tag">&lt;/insert&gt;</span></pre></div>

        
<p>
          如果你的数据库还支持多行插入, 你也可以传入一个 <tt>Author</tt> 数组或集合，并返回自动生成的主键。
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;insert</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"insertAuthor"</span><span class="pln"> </span><span class="atn">useGeneratedKeys</span><span class="pun">=</span><span class="atv">"true"</span><span class="pln">
    </span><span class="atn">keyProperty</span><span class="pun">=</span><span class="atv">"id"</span><span class="tag">&gt;</span><span class="pln">
  insert into Author (username, password, email, bio) values
  </span><span class="tag">&lt;foreach</span><span class="pln"> </span><span class="atn">item</span><span class="pun">=</span><span class="atv">"item"</span><span class="pln"> </span><span class="atn">collection</span><span class="pun">=</span><span class="atv">"list"</span><span class="pln"> </span><span class="atn">separator</span><span class="pun">=</span><span class="atv">","</span><span class="tag">&gt;</span><span class="pln">
    (#{item.username}, #{item.password}, #{item.email}, #{item.bio})
  </span><span class="tag">&lt;/foreach&gt;</span><span class="pln">
</span><span class="tag">&lt;/insert&gt;</span></pre></div>

        
<p>
          对于不支持自动生成类型的数据库或可能不支持自动生成主键的 JDBC 驱动，MyBatis 有另外一种方法来生成主键。
        </p>

        
<p>
          这里有一个简单（甚至很傻）的示例，它可以生成一个随机 ID（你最好不要这么做，但这里展示了
          MyBatis 处理问题的灵活性及其所关心的广度）：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;insert</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"insertAuthor"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;selectKey</span><span class="pln"> </span><span class="atn">keyProperty</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">resultType</span><span class="pun">=</span><span class="atv">"int"</span><span class="pln"> </span><span class="atn">order</span><span class="pun">=</span><span class="atv">"BEFORE"</span><span class="tag">&gt;</span><span class="pln">
    select CAST(RANDOM()*1000000 as INTEGER) a from SYSIBM.SYSDUMMY1
  </span><span class="tag">&lt;/selectKey&gt;</span><span class="pln">
  insert into Author
    (id, username, password, email,bio, favourite_section)
  values
    (#{id}, #{username}, #{password}, #{email}, #{bio}, #{favouriteSection,jdbcType=VARCHAR})
</span><span class="tag">&lt;/insert&gt;</span></pre></div>
        
<p>在上面的示例中，selectKey 元素中的语句将会首先运行，Author 的 id
        会被设置，然后插入语句会被调用。这可以提供给你一个与数据库中自动生成主键类似的行为，同时保持了 Java 代码的简洁。
        </p>
        
<p>selectKey 元素描述如下：
        </p>
        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;selectKey</span><span class="pln">
  </span><span class="atn">keyProperty</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln">
  </span><span class="atn">resultType</span><span class="pun">=</span><span class="atv">"int"</span><span class="pln">
  </span><span class="atn">order</span><span class="pun">=</span><span class="atv">"BEFORE"</span><span class="pln">
  </span><span class="atn">statementType</span><span class="pun">=</span><span class="atv">"PREPARED"</span><span class="tag">&gt;</span></pre></div>

        
<table border="0" class="table table-striped"><caption>selectKey 元素的属性</caption>
          
          <thead>
            
<tr class="a">
              
<th>属性</th>
              
<th>描述</th>
            </tr>
          </thead>
          <tbody>
            
<tr class="b">
              
<td><tt>keyProperty</tt></td>
              
<td>
                selectKey 语句结果应该被设置的目标属性。如果希望得到多个生成的列，也可以是逗号分隔的属性名称列表。
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>keyColumn</tt></td>
              
<td>
                匹配属性的返回结果集中的列名称。如果希望得到多个生成的列，也可以是逗号分隔的属性名称列表。
              </td>
            </tr>
            
<tr class="b">
              
<td><tt>resultType</tt></td>
              
<td>
                结果的类型。MyBatis 通常可以推断出来，但是为了更加精确，写上也不会有什么问题。MyBatis 允许将任何简单类型用作主键的类型，包括字符串。如果希望作用于多个生成的列，则可以使用一个包含期望属性的 Object 或一个 Map。
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>order</tt></td>
              
<td>
                这可以被设置为 BEFORE 或 AFTER。如果设置为
                BEFORE，那么它会首先生成主键，设置 keyProperty 然后执行插入语句。如果设置为
                AFTER，那么先执行插入语句，然后是 selectKey 中的语句 - 这和 Oracle
                数据库的行为相似，在插入语句内部可能有嵌入索引调用。
              </td>
            </tr>
            
<tr class="b">
              
<td><tt>statementType</tt></td>
              
<td>
                与前面相同，MyBatis 支持 STATEMENT，PREPARED 和 CALLABLE
                语句的映射类型，分别代表 PreparedStatement 和 CallableStatement 类型。
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      
<div class="section">
<h3><a name="sql"></a>sql</h3>
        
<p>
          这个元素可以被用来定义可重用的 SQL 代码段，这些 SQL
          代码可以被包含在其他语句中。它可以（在加载的时候）被静态地设置参数。
          在不同的包含语句中可以设置不同的值到参数占位符上。比如：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;sql</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"userColumns"</span><span class="tag">&gt;</span><span class="pln"> ${alias}.id,${alias}.username,${alias}.password </span><span class="tag">&lt;/sql&gt;</span></pre></div>

        
<p>
          这个 SQL 片段可以被包含在其他语句中，例如：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;select</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"selectUsers"</span><span class="pln"> </span><span class="atn">resultType</span><span class="pun">=</span><span class="atv">"map"</span><span class="tag">&gt;</span><span class="pln">
  select
    </span><span class="tag">&lt;include</span><span class="pln"> </span><span class="atn">refid</span><span class="pun">=</span><span class="atv">"userColumns"</span><span class="tag">&gt;&lt;property</span><span class="pln"> </span><span class="atn">name</span><span class="pun">=</span><span class="atv">"alias"</span><span class="pln"> </span><span class="atn">value</span><span class="pun">=</span><span class="atv">"t1"</span><span class="tag">/&gt;&lt;/include&gt;</span><span class="pln">,
    </span><span class="tag">&lt;include</span><span class="pln"> </span><span class="atn">refid</span><span class="pun">=</span><span class="atv">"userColumns"</span><span class="tag">&gt;&lt;property</span><span class="pln"> </span><span class="atn">name</span><span class="pun">=</span><span class="atv">"alias"</span><span class="pln"> </span><span class="atn">value</span><span class="pun">=</span><span class="atv">"t2"</span><span class="tag">/&gt;&lt;/include&gt;</span><span class="pln">
  from some_table t1
    cross join some_table t2
</span><span class="tag">&lt;/select&gt;</span></pre></div>

        
<p>
          属性值也可以被用在 include 元素的 refid 属性里或 include 元素的内部语句中，例如：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;sql</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"sometable"</span><span class="tag">&gt;</span><span class="pln">
  ${prefix}Table
</span><span class="tag">&lt;/sql&gt;</span><span class="pln">

</span><span class="tag">&lt;sql</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"someinclude"</span><span class="tag">&gt;</span><span class="pln">
  from
    </span><span class="tag">&lt;include</span><span class="pln"> </span><span class="atn">refid</span><span class="pun">=</span><span class="atv">"${include_target}"</span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;/sql&gt;</span><span class="pln">

</span><span class="tag">&lt;select</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"select"</span><span class="pln"> </span><span class="atn">resultType</span><span class="pun">=</span><span class="atv">"map"</span><span class="tag">&gt;</span><span class="pln">
  select
    field1, field2, field3
  </span><span class="tag">&lt;include</span><span class="pln"> </span><span class="atn">refid</span><span class="pun">=</span><span class="atv">"someinclude"</span><span class="tag">&gt;</span><span class="pln">
    </span><span class="tag">&lt;property</span><span class="pln"> </span><span class="atn">name</span><span class="pun">=</span><span class="atv">"prefix"</span><span class="pln"> </span><span class="atn">value</span><span class="pun">=</span><span class="atv">"Some"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;property</span><span class="pln"> </span><span class="atn">name</span><span class="pun">=</span><span class="atv">"include_target"</span><span class="pln"> </span><span class="atn">value</span><span class="pun">=</span><span class="atv">"sometable"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;/include&gt;</span><span class="pln">
</span><span class="tag">&lt;/select&gt;</span></pre></div>
      </div>

      <a name="Parameters"></a>
<div class="section" id="Parameters">
<h3><a name="a.E5.8F.82.E6.95.B0"></a>参数</h3>
        
<p>
          你之前见到的所有语句中，使用的都是简单参数。实际上参数是 MyBatis
          非常强大的元素。对于简单的使用场景，大约 90% 的情况下你都不需要使用复杂的参数，比如：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;select</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"selectUsers"</span><span class="pln"> </span><span class="atn">resultType</span><span class="pun">=</span><span class="atv">"User"</span><span class="tag">&gt;</span><span class="pln">
  select id, username, password
  from users
  where id = #{id}
</span><span class="tag">&lt;/select&gt;</span></pre></div>

        
<p>
          上面的这个示例说明了一个非常简单的命名参数映射。参数类型被设置为
          <tt>int</tt>，这样这个参数就可以被设置成任何内容。原始类型或简单数据类型（比如
          <tt>Integer</tt> 和 <tt>String</tt>）因为没有相关属性，它会完全用参数值来替代。
          然而，如果传入一个复杂的对象，行为就会有一点不同了。比如：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;insert</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"insertUser"</span><span class="pln"> </span><span class="atn">parameterType</span><span class="pun">=</span><span class="atv">"User"</span><span class="tag">&gt;</span><span class="pln">
  insert into users (id, username, password)
  values (#{id}, #{username}, #{password})
</span><span class="tag">&lt;/insert&gt;</span></pre></div>

        
<p>
          如果 User 类型的参数对象传递到了语句中，id、username 和 password
          属性将会被查找，然后将它们的值传入预处理语句的参数中。
        </p>

        
<p>
          对向语句中传递参数来说，这真是既简单又有效。不过参数映射的功能远不止于此。
        </p>

        
<p>
          首先，像 MyBatis 的其他部分一样，参数也可以指定一个特殊的数据类型。
        </p>

        
<div class="source"><pre class="prettyprint"><span class="com">#{property,javaType=int,jdbcType=NUMERIC}</span></pre></div>

        
<p>
          像 MyBatis 的其它部分一样，javaType 几乎总是可以根据参数对象的类型确定下来，除非该对象是一个
          <tt>HashMap</tt>。这个时候，你需要显式指定 <tt>javaType</tt>
          来确保正确的类型处理器（<tt>TypeHandler</tt>）被使用。
        </p>

        
<p><span class="label important">提示</span> JDBC 要求，如果一个列允许 null
        值，并且会传递值 null 的参数，就必须要指定 JDBC Type。阅读
        <tt>PreparedStatement.setNull()</tt>的 JavaDoc 文档来获取更多信息。
        </p>

        
<p>
          要更进一步地自定义类型处理方式，你也可以指定一个特殊的类型处理器类（或别名），比如：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="com">#{age,javaType=int,jdbcType=NUMERIC,typeHandler=MyTypeHandler}</span></pre></div>

        
<p>尽管看起来配置变得越来越繁琐，但实际上，很少需要如此繁琐的配置。</p>

        
<p>对于数值类型，还有一个小数保留位数的设置，来指定小数点后保留的位数。</p>

        
<div class="source"><pre class="prettyprint"><span class="com">#{height,javaType=double,jdbcType=NUMERIC,numericScale=2}</span></pre></div>

        
<p>
          最后，mode 属性允许你指定 <tt>IN</tt>，<tt>OUT</tt> 或
          <tt>INOUT</tt> 参数。如果参数的 <tt>mode</tt> 为 <tt>OUT</tt>
          或 <tt>INOUT</tt>，就像你在指定输出参数时所期望的行为那样，参数对象的属性实际值将会被改变。
          如果 <tt>mode</tt> 为 <tt>OUT</tt>（或 <tt>INOUT</tt>），而且
          <tt>jdbcType</tt> 为 <tt>CURSOR</tt>（也就是 Oracle 的
          REFCURSOR），你必须指定一个 <tt>resultMap</tt> 引用来将结果集
          <tt>ResultMap</tt> 映射到参数的类型上。要注意这里的
          <tt>javaType</tt> 属性是可选的，如果留空并且 jdbcType 是
          <tt>CURSOR</tt>，它会被自动地被设为 <tt>ResultMap</tt>。
        </p>

        
<div class="source"><pre class="prettyprint"><span class="com">#{department, mode=OUT, jdbcType=CURSOR, javaType=ResultSet, resultMap=departmentResultMap}</span></pre></div>

        
<p>
          MyBatis 也支持很多高级的数据类型，比如结构体（structs），但是当使用 out
          参数时，你必须显式设置类型的名称。比如（再次提示，在实际中要像这样不能换行）：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="com">#{middleInitial, mode=OUT, jdbcType=STRUCT, jdbcTypeName=MY_TYPE, resultMap=departmentResultMap}</span></pre></div>

        
<p>
          尽管所有这些选项很强大，但大多时候你只须简单地指定属性名，其他的事情
          MyBatis 会自己去推断，顶多要为可能为空的列指定 <tt>jdbcType</tt>。
        </p>

        
<div class="source"><pre class="prettyprint"><span class="com">#{firstName}</span><span class="pln">
</span><span class="com">#{middleInitial,jdbcType=VARCHAR}</span><span class="pln">
</span><span class="com">#{lastName}</span></pre></div>

        
<div class="section">
<h4><a name="a.E5.AD.97.E7.AC.A6.E4.B8.B2.E6.9B.BF.E6.8D.A2"></a>
          字符串替换
        </h4>

	      
<p>
          默认情况下,使用 <tt>#{}</tt>
          格式的语法会导致 MyBatis 创建 <tt>PreparedStatement</tt>
          参数占位符并安全地设置参数（就像使用 ? 一样）。
          这样做更安全，更迅速，通常也是首选做法，不过有时你就是想直接在 SQL 语句中插入一个不转义的字符串。
          比如，像 ORDER BY，你可以这样来使用：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="pln">ORDER BY $</span><span class="pun">{</span><span class="pln">columnName</span><span class="pun">}</span></pre></div>

        
<p>这里 MyBatis 不会修改或转义字符串。</p>

        
<p>
          当 SQL 语句中的元数据（如表名或列名）是动态生成的时候，字符串替换将会非常有用。
          举个例子，如果你想通过任何一列从表中 <tt>select</tt> 数据时，不需要像下面这样写：
          </p>
<div class="source"><pre class="prettyprint"><span class="lit">@Select</span><span class="pun">(</span><span class="str">"select * from user where id = #{id}"</span><span class="pun">)</span><span class="pln">
</span><span class="typ">User</span><span class="pln"> findById</span><span class="pun">(</span><span class="lit">@Param</span><span class="pun">(</span><span class="str">"id"</span><span class="pun">)</span><span class="pln"> </span><span class="kwd">long</span><span class="pln"> id</span><span class="pun">);</span><span class="pln">

</span><span class="lit">@Select</span><span class="pun">(</span><span class="str">"select * from user where name = #{name}"</span><span class="pun">)</span><span class="pln">
</span><span class="typ">User</span><span class="pln"> findByName</span><span class="pun">(</span><span class="lit">@Param</span><span class="pun">(</span><span class="str">"name"</span><span class="pun">)</span><span class="pln"> </span><span class="typ">String</span><span class="pln"> name</span><span class="pun">);</span><span class="pln">

</span><span class="lit">@Select</span><span class="pun">(</span><span class="str">"select * from user where email = #{email}"</span><span class="pun">)</span><span class="pln">
</span><span class="typ">User</span><span class="pln"> findByEmail</span><span class="pun">(</span><span class="lit">@Param</span><span class="pun">(</span><span class="str">"email"</span><span class="pun">)</span><span class="pln"> </span><span class="typ">String</span><span class="pln"> email</span><span class="pun">);</span><span class="pln">

</span><span class="com">// and more "findByXxx" method</span></pre></div>
          可以只写这样一个方法：
          
<div class="source"><pre class="prettyprint"><span class="lit">@Select</span><span class="pun">(</span><span class="str">"select * from user where ${column} = #{value}"</span><span class="pun">)</span><span class="pln">
</span><span class="typ">User</span><span class="pln"> findByColumn</span><span class="pun">(</span><span class="lit">@Param</span><span class="pun">(</span><span class="str">"column"</span><span class="pun">)</span><span class="pln"> </span><span class="typ">String</span><span class="pln"> column</span><span class="pun">,</span><span class="pln"> </span><span class="lit">@Param</span><span class="pun">(</span><span class="str">"value"</span><span class="pun">)</span><span class="pln"> </span><span class="typ">String</span><span class="pln"> value</span><span class="pun">);</span></pre></div>
          其中 <tt>${column}</tt> 会被直接替换，而 <tt>#{value}</tt> 会被使用 <tt>?</tt> 预处理。
          因此你就可以像下面这样来达到上述功能：
          
<div class="source"><pre class="prettyprint"><span class="typ">User</span><span class="pln"> userOfId1 </span><span class="pun">=</span><span class="pln"> userMapper</span><span class="pun">.</span><span class="pln">findByColumn</span><span class="pun">(</span><span class="str">"id"</span><span class="pun">,</span><span class="pln"> </span><span class="lit">1L</span><span class="pun">);</span><span class="pln">
</span><span class="typ">User</span><span class="pln"> userOfNameKid </span><span class="pun">=</span><span class="pln"> userMapper</span><span class="pun">.</span><span class="pln">findByColumn</span><span class="pun">(</span><span class="str">"name"</span><span class="pun">,</span><span class="pln"> </span><span class="str">"kid"</span><span class="pun">);</span><span class="pln">
</span><span class="typ">User</span><span class="pln"> userOfEmail </span><span class="pun">=</span><span class="pln"> userMapper</span><span class="pun">.</span><span class="pln">findByColumn</span><span class="pun">(</span><span class="str">"email"</span><span class="pun">,</span><span class="pln"> </span><span class="str">"noone@nowhere.com"</span><span class="pun">);</span></pre></div>
        

        
<p>
          这个想法也同样适用于用来替换表名的情况。
        </p>

        
<p>
          <span class="label important">提示</span>
          用这种方式接受用户的输入，并将其用于语句中的参数是不安全的，会导致潜在的 SQL
          注入攻击，因此要么不允许用户输入这些字段，要么自行转义并检验。
        </p>
      </div></div>

      <a name="Result_Maps"></a>
<div class="section" id="Result_Maps">
<h3><a name="a.E7.BB.93.E6.9E.9C.E6.98.A0.E5.B0.84"></a>结果映射</h3>
        
<p>
		      <tt>resultMap</tt> 元素是 MyBatis 中最重要最强大的元素。它可以让你从 90%
          的 JDBC <tt>ResultSets</tt> 数据提取代码中解放出来，并在一些情形下允许你进行一些
          JDBC 不支持的操作。实际上，在为一些比如连接的复杂语句编写映射代码的时候，一份
          <tt>resultMap</tt> 能够代替实现同等功能的长达数千行的代码。ResultMap
          的设计思想是，对于简单的语句根本不需要配置显式的结果映射，而对于复杂一点的语句只需要描述它们的关系就行了。
        </p>

        
<p>
          你已经见过简单映射语句的示例了，但并没有显式指定 <tt>resultMap</tt>。比如：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;select</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"selectUsers"</span><span class="pln"> </span><span class="atn">resultType</span><span class="pun">=</span><span class="atv">"map"</span><span class="tag">&gt;</span><span class="pln">
  select id, username, hashedPassword
  from some_table
  where id = #{id}
</span><span class="tag">&lt;/select&gt;</span></pre></div>

        
<p>
		      上述语句只是简单地将所有的列映射到 <tt>HashMap</tt> 的键上，这由 <tt>resultType</tt> 属性指定。虽然在大部分情况下都够用，但是 HashMap 不是一个很好的领域模型。你的程序更可能会使用 JavaBean 或 POJO（Plain Old Java Objects，普通老式 Java 对象）作为领域模型。MyBatis 对两者都提供了支持。看看下面这个 JavaBean：
        </p>
        
<div class="source"><pre class="prettyprint"><span class="kwd">package</span><span class="pln"> com</span><span class="pun">.</span><span class="pln">someapp</span><span class="pun">.</span><span class="pln">model</span><span class="pun">;</span><span class="pln">
</span><span class="kwd">public</span><span class="pln"> </span><span class="kwd">class</span><span class="pln"> </span><span class="typ">User</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
  </span><span class="kwd">private</span><span class="pln"> </span><span class="kwd">int</span><span class="pln"> id</span><span class="pun">;</span><span class="pln">
  </span><span class="kwd">private</span><span class="pln"> </span><span class="typ">String</span><span class="pln"> username</span><span class="pun">;</span><span class="pln">
  </span><span class="kwd">private</span><span class="pln"> </span><span class="typ">String</span><span class="pln"> hashedPassword</span><span class="pun">;</span><span class="pln">

  </span><span class="kwd">public</span><span class="pln"> </span><span class="kwd">int</span><span class="pln"> getId</span><span class="pun">()</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    </span><span class="kwd">return</span><span class="pln"> id</span><span class="pun">;</span><span class="pln">
  </span><span class="pun">}</span><span class="pln">
  </span><span class="kwd">public</span><span class="pln"> </span><span class="kwd">void</span><span class="pln"> setId</span><span class="pun">(</span><span class="kwd">int</span><span class="pln"> id</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    </span><span class="kwd">this</span><span class="pun">.</span><span class="pln">id </span><span class="pun">=</span><span class="pln"> id</span><span class="pun">;</span><span class="pln">
  </span><span class="pun">}</span><span class="pln">
  </span><span class="kwd">public</span><span class="pln"> </span><span class="typ">String</span><span class="pln"> getUsername</span><span class="pun">()</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    </span><span class="kwd">return</span><span class="pln"> username</span><span class="pun">;</span><span class="pln">
  </span><span class="pun">}</span><span class="pln">
  </span><span class="kwd">public</span><span class="pln"> </span><span class="kwd">void</span><span class="pln"> setUsername</span><span class="pun">(</span><span class="typ">String</span><span class="pln"> username</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    </span><span class="kwd">this</span><span class="pun">.</span><span class="pln">username </span><span class="pun">=</span><span class="pln"> username</span><span class="pun">;</span><span class="pln">
  </span><span class="pun">}</span><span class="pln">
  </span><span class="kwd">public</span><span class="pln"> </span><span class="typ">String</span><span class="pln"> getHashedPassword</span><span class="pun">()</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    </span><span class="kwd">return</span><span class="pln"> hashedPassword</span><span class="pun">;</span><span class="pln">
  </span><span class="pun">}</span><span class="pln">
  </span><span class="kwd">public</span><span class="pln"> </span><span class="kwd">void</span><span class="pln"> setHashedPassword</span><span class="pun">(</span><span class="typ">String</span><span class="pln"> hashedPassword</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    </span><span class="kwd">this</span><span class="pun">.</span><span class="pln">hashedPassword </span><span class="pun">=</span><span class="pln"> hashedPassword</span><span class="pun">;</span><span class="pln">
  </span><span class="pun">}</span><span class="pln">
</span><span class="pun">}</span></pre></div>

        
<p>
          基于 JavaBean 的规范，上面这个类有 3 个属性：id，username 和
          hashedPassword。这些属性会对应到 select 语句中的列名。
        </p>

        
<p>
          这样的一个 JavaBean 可以被映射到 <tt>ResultSet</tt>，就像映射到
          <tt>HashMap</tt> 一样简单。
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;select</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"selectUsers"</span><span class="pln"> </span><span class="atn">resultType</span><span class="pun">=</span><span class="atv">"com.someapp.model.User"</span><span class="tag">&gt;</span><span class="pln">
  select id, username, hashedPassword
  from some_table
  where id = #{id}
</span><span class="tag">&lt;/select&gt;</span></pre></div>

        
<p>
          类型别名是你的好帮手。使用它们，你就可以不用输入类的完全限定名称了。比如：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="com">&lt;!-- mybatis-config.xml 中 --&gt;</span><span class="pln">
</span><span class="tag">&lt;typeAlias</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"com.someapp.model.User"</span><span class="pln"> </span><span class="atn">alias</span><span class="pun">=</span><span class="atv">"User"</span><span class="tag">/&gt;</span><span class="pln">

</span><span class="com">&lt;!-- SQL 映射 XML 中 --&gt;</span><span class="pln">
</span><span class="tag">&lt;select</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"selectUsers"</span><span class="pln"> </span><span class="atn">resultType</span><span class="pun">=</span><span class="atv">"User"</span><span class="tag">&gt;</span><span class="pln">
  select id, username, hashedPassword
  from some_table
  where id = #{id}
</span><span class="tag">&lt;/select&gt;</span></pre></div>

        
<p>
          这些情况下，MyBatis 会在幕后自动创建一个 <tt>ResultMap</tt>，再基于属性名来映射列到
          JavaBean 的属性上。如果列名和属性名没有精确匹配，可以在 SELECT
          语句中对列使用别名（这是一个基本的 SQL 特性）来匹配标签。比如：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;select</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"selectUsers"</span><span class="pln"> </span><span class="atn">resultType</span><span class="pun">=</span><span class="atv">"User"</span><span class="tag">&gt;</span><span class="pln">
  select
    user_id             as "id",
    user_name           as "userName",
    hashed_password     as "hashedPassword"
  from some_table
  where id = #{id}
</span><span class="tag">&lt;/select&gt;</span></pre></div>

        
<p>
          <tt>ResultMap</tt> 最优秀的地方在于，虽然你已经对它相当了解了，但是根本就不需要显式地用到他们。
          上面这些简单的示例根本不需要下面这些繁琐的配置。
          但出于示范的原因，让我们来看看最后一个示例中，如果使用外部的
          <tt>resultMap</tt> 会怎样，这也是解决列名不匹配的另外一种方式。
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;resultMap</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"userResultMap"</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"User"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"user_id"</span><span class="pln"> </span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"username"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"user_name"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"password"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"hashed_password"</span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;/resultMap&gt;</span></pre></div>

        
<p>
          而在引用它的语句中使用 <tt>resultMap</tt> 属性就行了（注意我们去掉了
          <tt>resultType</tt> 属性）。比如:
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;select</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"selectUsers"</span><span class="pln"> </span><span class="atn">resultMap</span><span class="pun">=</span><span class="atv">"userResultMap"</span><span class="tag">&gt;</span><span class="pln">
  select user_id, user_name, hashed_password
  from some_table
  where id = #{id}
</span><span class="tag">&lt;/select&gt;</span></pre></div>

        
<p>
          如果世界总是这么简单就好了。
        </p>

	      
<div class="section">
<h4><a name="a.E9.AB.98.E7.BA.A7.E7.BB.93.E6.9E.9C.E6.98.A0.E5.B0.84"></a>高级结果映射</h4>

        
<p>
          MyBatis 创建时的一个思想是：数据库不可能永远是你所想或所需的那个样子。
          我们希望每个数据库都具备良好的第三范式或 BCNF 范式，可惜它们不总都是这样。
          如果能有一种完美的数据库映射模式，所有应用程序都可以使用它，那就太好了，但可惜也没有。
          而 ResultMap 就是 MyBatis 对这个问题的答案。
        </p>

        
<p>
          比如，我们如何映射下面这个语句？
        </p>

        
<div class="source"><pre class="prettyprint"><span class="com">&lt;!-- 非常复杂的语句 --&gt;</span><span class="pln">
</span><span class="tag">&lt;select</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"selectBlogDetails"</span><span class="pln"> </span><span class="atn">resultMap</span><span class="pun">=</span><span class="atv">"detailedBlogResultMap"</span><span class="tag">&gt;</span><span class="pln">
  select
       B.id as blog_id,
       B.title as blog_title,
       B.author_id as blog_author_id,
       A.id as author_id,
       A.username as author_username,
       A.password as author_password,
       A.email as author_email,
       A.bio as author_bio,
       A.favourite_section as author_favourite_section,
       P.id as post_id,
       P.blog_id as post_blog_id,
       P.author_id as post_author_id,
       P.created_on as post_created_on,
       P.section as post_section,
       P.subject as post_subject,
       P.draft as draft,
       P.body as post_body,
       C.id as comment_id,
       C.post_id as comment_post_id,
       C.name as comment_name,
       C.comment as comment_text,
       T.id as tag_id,
       T.name as tag_name
  from Blog B
       left outer join Author A on B.author_id = A.id
       left outer join Post P on B.id = P.blog_id
       left outer join Comment C on P.id = C.post_id
       left outer join Post_Tag PT on PT.post_id = P.id
       left outer join Tag T on PT.tag_id = T.id
  where B.id = #{id}
</span><span class="tag">&lt;/select&gt;</span></pre></div>

        
<p>
          你可能想把它映射到一个智能的对象模型，这个对象表示了一篇博客，它由某位作者所写，有很多的博文，每篇博文有零或多条的评论和标签。
          我们来看看下面这个完整的例子，它是一个非常复杂的结果映射（假设作者，博客，博文，评论和标签都是类型别名）。
          不用紧张，我们会一步一步来说明。虽然它看起来令人望而生畏，但其实非常简单。
        </p>

        
<div class="source"><pre class="prettyprint"><span class="com">&lt;!-- 非常复杂的结果映射 --&gt;</span><span class="pln">
</span><span class="tag">&lt;resultMap</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"detailedBlogResultMap"</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"Blog"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;constructor&gt;</span><span class="pln">
    </span><span class="tag">&lt;idArg</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"blog_id"</span><span class="pln"> </span><span class="atn">javaType</span><span class="pun">=</span><span class="atv">"int"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;/constructor&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"title"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"blog_title"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;association</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"author"</span><span class="pln"> </span><span class="atn">javaType</span><span class="pun">=</span><span class="atv">"Author"</span><span class="tag">&gt;</span><span class="pln">
    </span><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_id"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"username"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_username"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"password"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_password"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"email"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_email"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"bio"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_bio"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"favouriteSection"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_favourite_section"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;/association&gt;</span><span class="pln">
  </span><span class="tag">&lt;collection</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"posts"</span><span class="pln"> </span><span class="atn">ofType</span><span class="pun">=</span><span class="atv">"Post"</span><span class="tag">&gt;</span><span class="pln">
    </span><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"post_id"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"subject"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"post_subject"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;association</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"author"</span><span class="pln"> </span><span class="atn">javaType</span><span class="pun">=</span><span class="atv">"Author"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;collection</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"comments"</span><span class="pln"> </span><span class="atn">ofType</span><span class="pun">=</span><span class="atv">"Comment"</span><span class="tag">&gt;</span><span class="pln">
      </span><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"comment_id"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;/collection&gt;</span><span class="pln">
    </span><span class="tag">&lt;collection</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"tags"</span><span class="pln"> </span><span class="atn">ofType</span><span class="pun">=</span><span class="atv">"Tag"</span><span class="pln"> </span><span class="tag">&gt;</span><span class="pln">
      </span><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"tag_id"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;/collection&gt;</span><span class="pln">
    </span><span class="tag">&lt;discriminator</span><span class="pln"> </span><span class="atn">javaType</span><span class="pun">=</span><span class="atv">"int"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"draft"</span><span class="tag">&gt;</span><span class="pln">
      </span><span class="tag">&lt;case</span><span class="pln"> </span><span class="atn">value</span><span class="pun">=</span><span class="atv">"1"</span><span class="pln"> </span><span class="atn">resultType</span><span class="pun">=</span><span class="atv">"DraftPost"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;/discriminator&gt;</span><span class="pln">
  </span><span class="tag">&lt;/collection&gt;</span><span class="pln">
</span><span class="tag">&lt;/resultMap&gt;</span></pre></div>

        
<p>
          <tt>resultMap</tt> 元素有很多子元素和一个值得深入探讨的结构。
          下面是<tt>resultMap</tt> 元素的概念视图。
        </p>

        </div>
<div class="section">
<h4><a name="a.E7.BB.93.E6.9E.9C.E6.98.A0.E5.B0.84.EF.BC.88resultMap.EF.BC.89"></a>结果映射（resultMap）</h4>
        
<ul>
          
<li>
            <tt>constructor</tt> - 用于在实例化类时，注入结果到构造方法中
            
<ul>
              
<li><tt>idArg</tt> - ID 参数；标记出作为 ID 的结果可以帮助提高整体性能</li>
              
<li><tt>arg</tt> - 将被注入到构造方法的一个普通结果</li>
            </ul>
          </li>
          
<li><tt>id</tt> – 一个 ID 结果；标记出作为 ID 的结果可以帮助提高整体性能</li>
          
<li><tt>result</tt> – 注入到字段或 JavaBean 属性的普通结果</li>
          
<li>
            <tt>association</tt> – 一个复杂类型的关联；许多结果将包装成这种类型
            
<ul>
              
<li>嵌套结果映射 – 关联本身可以是一个 <tt>resultMap</tt> 元素，或者从别处引用一个</li>
            </ul>
          </li>
          
<li>
            <tt>collection</tt> – 一个复杂类型的集合
            
<ul>
              
<li>嵌套结果映射 – 集合本身可以是一个 <tt>resultMap</tt> 元素，或者从别处引用一个</li>
            </ul>
          </li>
          
<li>
            <tt>discriminator</tt> – 使用结果值来决定使用哪个 <tt>resultMap</tt>
            
<ul>
              
<li>
                <tt>case</tt> – 基于某些值的结果映射
                
<ul>
		              
<li>嵌套结果映射 – <tt>case</tt> 本身可以是一个 <tt>resultMap</tt> 元素，因此可以具有相同的结构和元素，或者从别处引用一个</li>
                </ul>
              </li>
            </ul>
          </li>
        </ul>

        
<table border="0" class="table table-striped"><caption>ResultMap 的属性列表</caption>
          
          <thead>
            
<tr class="a">
              
<th>属性</th>
              
<th>描述</th>
            </tr>
          </thead>
          <tbody>
            
<tr class="b">
              
<td><tt>id</tt></td>
              
<td>当前命名空间中的一个唯一标识，用于标识一个结果映射。</td>
            </tr>
            
<tr class="a">
              
<td><tt>type</tt></td>
              
<td>
                类的完全限定名, 或者一个类型别名（关于内置的类型别名，可以参考上面的表格）。
              </td>
            </tr>
            
<tr class="b">
              
<td><tt>autoMapping</tt></td>
              
<td>
                如果设置这个属性，MyBatis将会为本结果映射开启或者关闭自动映射。
                这个属性会覆盖全局的属性 autoMappingBehavior。默认值：未设置（unset）。
              </td>
            </tr>
          </tbody>
        </table>

        
<p>
          <span class="label important">最佳实践</span>
          最好一步步地建立结果映射。单元测试可以在这个过程中起到很大帮助。
          如果你尝试一次创建一个像上面示例那样的巨大的结果映射，那么很可能会出现错误而且很难去使用它来完成工作。
          从最简单的形态开始，逐步迭代。而且别忘了单元测试！
          使用框架的缺点是有时候它们看上去像黑盒子（无论源代码是否可见）。
          为了确保你实现的行为和想要的一致，最好的选择是编写单元测试。提交 bug
          的时候它也能起到很大的作用。
        </p>

        
<p>
          下一部分将详细说明每个元素。
        </p>

        </div>
<div class="section">
<h4><a name="id_.26_result"></a>id &amp; result</h4>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"post_id"</span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"subject"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"post_subject"</span><span class="tag">/&gt;</span></pre></div>

        
<p>
          这些是结果映射最基本的内容。<i>id</i> 和 <i>result</i>
          元素都将一个列的值映射到一个简单数据类型（String, int, double, Date
          等）的属性或字段。
        </p>

        
<p>
          这两者之间的唯一不同是，<i>id</i>
          元素表示的结果将是对象的标识属性，这会在比较对象实例时用到。
          这样可以提高整体的性能，尤其是进行缓存和嵌套结果映射（也就是连接映射）的时候。
        </p>

        
<p>
          两个元素都有一些属性：
        </p>

        
<table border="0" class="table table-striped"><caption>Id 和 Result 的属性</caption>
          
          <thead>
            
<tr class="a">
              
<th>属性</th>
              
<th>描述</th>
            </tr>
          </thead>
          <tbody>
            
<tr class="b">
              
<td><tt>property</tt></td>
              
<td>
                映射到列结果的字段或属性。如果用来匹配的 JavaBean
                存在给定名字的属性，那么它将会被使用。否则 MyBatis 将会寻找给定名称的字段。
                无论是哪一种情形，你都可以使用通常的点式分隔形式进行复杂属性导航。
                比如，你可以这样映射一些简单的东西：“username”，或者映射到一些复杂的东西上：“address.street.number”。
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>column</tt></td>
              
<td>
                数据库中的列名，或者是列的别名。一般情况下，这和传递给
                <tt>resultSet.getString(columnName)</tt> 方法的参数一样。
              </td>
            </tr>
            
<tr class="b">
              
<td><tt>javaType</tt></td>
              
<td>
                一个 Java 类的完全限定名，或一个类型别名（关于内置的类型别名，可以参考上面的表格）。
                如果你映射到一个 JavaBean，MyBatis 通常可以推断类型。然而，如果你映射到的是
                HashMap，那么你应该明确地指定 javaType 来保证行为与期望的相一致。
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>jdbcType</tt></td>
              
<td>
                JDBC 类型，所支持的 JDBC 类型参见这个表格之后的“支持的 JDBC 类型”。
                只需要在可能执行插入、更新和删除的且允许空值的列上指定 JDBC 类型。这是
                JDBC 的要求而非 MyBatis 的要求。如果你直接面向 JDBC
                编程，你需要对可能存在空值的列指定这个类型。
              </td>
            </tr>
            
<tr class="b">
              
<td><tt>typeHandler</tt></td>
              
<td>
                我们在前面讨论过默认的类型处理器。使用这个属性，你可以覆盖默认的类型处理器。
                这个属性值是一个类型处理器实现类的完全限定名，或者是类型别名。
              </td>
            </tr>
          </tbody>
        </table>

        </div>
<div class="section">
<h4><a name="a.E6.94.AF.E6.8C.81.E7.9A.84_JDBC_.E7.B1.BB.E5.9E.8B"></a>支持的 JDBC 类型</h4>

        
<p>
          为了以后可能的使用场景，MyBatis 通过内置的 jdbcType 枚举类型支持下面的 JDBC 类型。
        </p>

        
<table border="0" class="table table-striped">
          
<tbody><tr class="a">
            
<td><tt>BIT</tt></td>
            
<td><tt>FLOAT</tt></td>
            
<td><tt>CHAR</tt></td>
            
<td><tt>TIMESTAMP</tt></td>
            
<td><tt>OTHER</tt></td>
            
<td><tt>UNDEFINED</tt></td>
          </tr>
          
<tr class="b">
            
<td><tt>TINYINT</tt></td>
            
<td><tt>REAL</tt></td>
            
<td><tt>VARCHAR</tt></td>
            
<td><tt>BINARY</tt></td>
            
<td><tt>BLOB</tt></td>
            
<td><tt>NVARCHAR</tt></td>
          </tr>
          
<tr class="a">
            
<td><tt>SMALLINT</tt></td>
            
<td><tt>DOUBLE</tt></td>
            
<td><tt>LONGVARCHAR</tt></td>
            
<td><tt>VARBINARY</tt></td>
            
<td><tt>CLOB</tt></td>
            
<td><tt>NCHAR</tt></td>
          </tr>
          
<tr class="b">
            
<td><tt>INTEGER</tt></td>
            
<td><tt>NUMERIC</tt></td>
            
<td><tt>DATE</tt></td>
            
<td><tt>LONGVARBINARY</tt></td>
            
<td><tt>BOOLEAN</tt></td>
            
<td><tt>NCLOB</tt></td>
          </tr>
          
<tr class="a">
            
<td><tt>BIGINT</tt></td>
            
<td><tt>DECIMAL</tt></td>
            
<td><tt>TIME</tt></td>
            
<td><tt>NULL</tt></td>
            
<td><tt>CURSOR</tt></td>
            
<td><tt>ARRAY</tt></td>
          </tr>
        </tbody></table>

        </div>
<div class="section">
<h4><a name="a.E6.9E.84.E9.80.A0.E6.96.B9.E6.B3.95"></a>构造方法</h4>

        
<p>
          通过修改对象属性的方式，可以满足大多数的数据传输对象（Data Transfer Object,
          DTO）以及绝大部分领域模型的要求。但有些情况下你想使用不可变类。
          一般来说，很少改变或基本不变的包含引用或数据的表，很适合使用不可变类。
          构造方法注入允许你在初始化时为类设置属性的值，而不用暴露出公有方法。MyBatis
          也支持私有属性和私有 JavaBean 属性来完成注入，但有一些人更青睐于通过构造方法进行注入。
          <i>constructor</i> 元素就是为此而生的。
        </p>

        
<p>
          看看下面这个构造方法:
        </p>

        
<div class="source"><pre class="prettyprint"><span class="kwd">public</span><span class="pln"> </span><span class="kwd">class</span><span class="pln"> </span><span class="typ">User</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
   </span><span class="com">//...</span><span class="pln">
   </span><span class="kwd">public</span><span class="pln"> </span><span class="typ">User</span><span class="pun">(</span><span class="typ">Integer</span><span class="pln"> id</span><span class="pun">,</span><span class="pln"> </span><span class="typ">String</span><span class="pln"> username</span><span class="pun">,</span><span class="pln"> </span><span class="kwd">int</span><span class="pln"> age</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
     </span><span class="com">//...</span><span class="pln">
  </span><span class="pun">}</span><span class="pln">
</span><span class="com">//...</span><span class="pln">
</span><span class="pun">}</span></pre></div>

        
<p>
          为了将结果注入构造方法，MyBatis 需要通过某种方式定位相应的构造方法。
          在下面的例子中，MyBatis 搜索一个声明了三个形参的的构造方法，参数类型以
          <tt>java.lang.Integer</tt>, <tt>java.lang.String</tt> 和
          <tt>int</tt> 的顺序给出。
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;constructor&gt;</span><span class="pln">
   </span><span class="tag">&lt;idArg</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">javaType</span><span class="pun">=</span><span class="atv">"int"</span><span class="tag">/&gt;</span><span class="pln">
   </span><span class="tag">&lt;arg</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"username"</span><span class="pln"> </span><span class="atn">javaType</span><span class="pun">=</span><span class="atv">"String"</span><span class="tag">/&gt;</span><span class="pln">
   </span><span class="tag">&lt;arg</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"age"</span><span class="pln"> </span><span class="atn">javaType</span><span class="pun">=</span><span class="atv">"_int"</span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;/constructor&gt;</span></pre></div>

        
<p>
          当你在处理一个带有多个形参的构造方法时，很容易搞乱 arg 元素的顺序。
          从版本 3.4.3 开始，可以在指定参数名称的前提下，以任意顺序编写 arg 元素。
          为了通过名称来引用构造方法参数，你可以添加 <tt>@Param</tt> 注解，或者使用
          '-parameters' 编译选项并启用 <tt>useActualParamName</tt>
          选项（默认开启）来编译项目。下面是一个等价的例子，尽管函数签名中第二和第三个形参的顺序与
          constructor 元素中参数声明的顺序不匹配。
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;constructor&gt;</span><span class="pln">
   </span><span class="tag">&lt;idArg</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">javaType</span><span class="pun">=</span><span class="atv">"int"</span><span class="pln"> </span><span class="atn">name</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="tag">/&gt;</span><span class="pln">
   </span><span class="tag">&lt;arg</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"age"</span><span class="pln"> </span><span class="atn">javaType</span><span class="pun">=</span><span class="atv">"_int"</span><span class="pln"> </span><span class="atn">name</span><span class="pun">=</span><span class="atv">"age"</span><span class="pln"> </span><span class="tag">/&gt;</span><span class="pln">
   </span><span class="tag">&lt;arg</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"username"</span><span class="pln"> </span><span class="atn">javaType</span><span class="pun">=</span><span class="atv">"String"</span><span class="pln"> </span><span class="atn">name</span><span class="pun">=</span><span class="atv">"username"</span><span class="pln"> </span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;/constructor&gt;</span></pre></div>

        
<p>
          如果存在名称和类型相同的属性，那么可以省略 <tt>javaType</tt> 。
        </p>

        
<p>
          剩余的属性和规则和普通的 id 和 result 元素是一样的。
        </p>

        
<table border="0" class="table table-striped">
          <thead>
            
<tr class="a">
              
<th>属性</th>
              
<th>描述</th>
            </tr>
          </thead>
          <tbody>
            
<tr class="b">
              
<td><tt>column</tt></td>
              
<td>
                数据库中的列名，或者是列的别名。一般情况下，这和传递给
                <tt>resultSet.getString(columnName)</tt> 方法的参数一样。
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>javaType</tt></td>
              
<td>
                一个 Java 类的完全限定名，或一个类型别名（关于内置的类型别名，可以参考上面的表格）。
                如果你映射到一个 JavaBean，MyBatis 通常可以推断类型。然而，如果你映射到的是
                HashMap，那么你应该明确地指定 javaType 来保证行为与期望的相一致。
              </td>
            </tr>
            
<tr class="b">
              
<td><tt>jdbcType</tt></td>
              
<td>
                JDBC 类型，所支持的 JDBC 类型参见这个表格之前的“支持的 JDBC 类型”。
                只需要在可能执行插入、更新和删除的且允许空值的列上指定 JDBC 类型。这是
                JDBC 的要求而非 MyBatis 的要求。如果你直接面向 JDBC
                编程，你需要对可能存在空值的列指定这个类型。
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>typeHandler</tt></td>
              
<td>
                我们在前面讨论过默认的类型处理器。使用这个属性，你可以覆盖默认的类型处理器。
                这个属性值是一个类型处理器实现类的完全限定名，或者是类型别名。
              </td>
            </tr>
            
<tr class="b">
              
<td><tt>select</tt></td>
              
<td>
                用于加载复杂类型属性的映射语句的 ID，它会从 column
                属性中指定的列检索数据，作为参数传递给此 select 语句。具体请参考关联元素。
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>resultMap</tt></td>
              
<td>
                结果映射的 ID，可以将嵌套的结果集映射到一个合适的对象树中。
                它可以作为使用额外 select 语句的替代方案。它可以将多表连接操作的结果映射成一个单一的
                <tt>ResultSet</tt>。这样的 <tt>ResultSet</tt>
                将会将包含重复或部分数据重复的结果集。为了将结果集正确地映射到嵌套的对象树中，MyBatis
                允许你 “串联”结果映射，以便解决嵌套结果集的问题。想了解更多内容，请参考下面的关联元素。
              </td>
            </tr>
            
<tr class="b">
              
<td><tt>name</tt></td>
              
<td>
                构造方法形参的名字。从 3.4.3 版本开始，通过指定具体的参数名，你可以以任意顺序写入
                arg 元素。参看上面的解释。
              </td>
            </tr>
          </tbody>
        </table>

        </div>
<div class="section">
<h4><a name="a.E5.85.B3.E8.81.94"></a>关联</h4>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;association</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"author"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"blog_author_id"</span><span class="pln"> </span><span class="atn">javaType</span><span class="pun">=</span><span class="atv">"Author"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_id"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"username"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_username"</span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;/association&gt;</span></pre></div>

        
<p>
          关联（association）元素处理“有一个”类型的关系。
          比如，在我们的示例中，一个博客有一个用户。关联结果映射和其它类型的映射工作方式差不多。
          你需要指定目标属性名以及属性的<tt>javaType</tt>（很多时候 MyBatis
          可以自己推断出来），在必要的情况下你还可以设置 JDBC
          类型，如果你想覆盖获取结果值的过程，还可以设置类型处理器。
        </p>

        
<p>
          关联的不同之处是，你需要告诉 MyBatis 如何加载关联。MyBatis 有两种不同的方式加载关联：
        </p>

        
<ul>
          
<li>
            嵌套 Select 查询：通过执行另外一个 SQL 映射语句来加载期望的复杂类型。
          </li>
          
<li>
            嵌套结果映射：使用嵌套的结果映射来处理连接结果的重复子集。
          </li>
        </ul>

        
<p>
          首先，先让我们来看看这个元素的属性。你将会发现，和普通的结果映射相比，它只在 select 和 resultMap 属性上有所不同。
        </p>

        
<table border="0" class="table table-striped">
          <thead>
            
<tr class="a">
              
<th>属性</th>
              
<th>描述</th>
            </tr>
          </thead>
          <tbody>
            
<tr class="b">
              
<td><tt>property</tt></td>
              
<td>
                映射到列结果的字段或属性。如果用来匹配的 JavaBean
                存在给定名字的属性，那么它将会被使用。否则 MyBatis 将会寻找给定名称的字段。
                无论是哪一种情形，你都可以使用通常的点式分隔形式进行复杂属性导航。
                比如，你可以这样映射一些简单的东西：“username”，或者映射到一些复杂的东西上：“address.street.number”。
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>javaType</tt></td>
              
<td>
                一个 Java 类的完全限定名，或一个类型别名（关于内置的类型别名，可以参考上面的表格）。
                如果你映射到一个 JavaBean，MyBatis 通常可以推断类型。然而，如果你映射到的是
                HashMap，那么你应该明确地指定 javaType 来保证行为与期望的相一致。
              </td>
            </tr>
            
<tr class="b">
              
<td><tt>jdbcType</tt></td>
              
<td>
                JDBC 类型，所支持的 JDBC 类型参见这个表格之前的“支持的 JDBC 类型”。
                只需要在可能执行插入、更新和删除的且允许空值的列上指定 JDBC 类型。这是
                JDBC 的要求而非 MyBatis 的要求。如果你直接面向
                JDBC 编程，你需要对可能存在空值的列指定这个类型。
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>typeHandler</tt></td>
              
<td>
                我们在前面讨论过默认的类型处理器。使用这个属性，你可以覆盖默认的类型处理器。
                这个属性值是一个类型处理器实现类的完全限定名，或者是类型别名。
              </td>
            </tr>
          </tbody>
        </table>

        </div>
<div class="section">
<h4><a name="a.E5.85.B3.E8.81.94.E7.9A.84.E5.B5.8C.E5.A5.97_Select_.E6.9F.A5.E8.AF.A2"></a>关联的嵌套 Select 查询</h4>

        
<table border="0" class="table table-striped">
          <thead>
            
<tr class="a">
              
<th>属性</th>
              
<th>描述</th>
            </tr>
          </thead>
          <tbody>
            
<tr class="b">
              
<td><tt>column</tt></td>
              
<td>
              数据库中的列名，或者是列的别名。一般情况下，这和传递给
              <tt>resultSet.getString(columnName)</tt> 方法的参数一样。
              注意：在使用复合主键的时候，你可以使用 <tt>column="{prop1=col1,prop2=col2}"</tt>
              这样的语法来指定多个传递给嵌套 Select 查询语句的列名。这会使得
              <tt>prop1</tt> 和 <tt>prop2</tt> 作为参数对象，被设置为对应嵌套 Select 语句的参数。
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>select</tt></td>
              
<td>
                用于加载复杂类型属性的映射语句的 ID，它会从 column
                属性指定的列中检索数据，作为参数传递给目标 select 语句。
                具体请参考下面的例子。注意：在使用复合主键的时候，你可以使用
                <tt>column="{prop1=col1,prop2=col2}"</tt> 这样的语法来指定多个传递给嵌套
                Select 查询语句的列名。这会使得 <tt>prop1</tt> 和 <tt>prop2</tt>
                作为参数对象，被设置为对应嵌套 Select 语句的参数。
              </td>
            </tr>
            
<tr class="b">
              
<td><tt>fetchType</tt></td>
              
<td>
                可选的。有效值为 <tt>lazy</tt> 和 <tt>eager</tt>。
                指定属性后，将在映射中忽略全局配置参数 <tt>lazyLoadingEnabled</tt>，使用属性的值。
              </td>
            </tr>
          </tbody>
        </table>

        
<p>
          示例：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;resultMap</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"blogResult"</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"Blog"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;association</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"author"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_id"</span><span class="pln"> </span><span class="atn">javaType</span><span class="pun">=</span><span class="atv">"Author"</span><span class="pln"> </span><span class="atn">select</span><span class="pun">=</span><span class="atv">"selectAuthor"</span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;/resultMap&gt;</span><span class="pln">

</span><span class="tag">&lt;select</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"selectBlog"</span><span class="pln"> </span><span class="atn">resultMap</span><span class="pun">=</span><span class="atv">"blogResult"</span><span class="tag">&gt;</span><span class="pln">
  SELECT * FROM BLOG WHERE ID = #{id}
</span><span class="tag">&lt;/select&gt;</span><span class="pln">

</span><span class="tag">&lt;select</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"selectAuthor"</span><span class="pln"> </span><span class="atn">resultType</span><span class="pun">=</span><span class="atv">"Author"</span><span class="tag">&gt;</span><span class="pln">
  SELECT * FROM AUTHOR WHERE ID = #{id}
</span><span class="tag">&lt;/select&gt;</span></pre></div>

        
<p>
          就是这么简单。我们有两个 select
          查询语句：一个用来加载博客（Blog），另外一个用来加载作者（Author），而且博客的结果映射描述了应该使用
          <tt>selectAuthor</tt> 语句加载它的 author 属性。
        </p>

        
<p>
          其它所有的属性将会被自动加载，只要它们的列名和属性名相匹配。
        </p>

        
<p>
          这种方式虽然很简单，但在大型数据集或大型数据表上表现不佳。这个问题被称为“N+1 查询问题”。
          概括地讲，N+1 查询问题是这样子的：
        </p>

        
<ul>
          
<li>你执行了一个单独的 SQL 语句来获取结果的一个列表（就是“+1”）。</li>
          
<li>对列表返回的每条记录，你执行一个 select 查询语句来为每条记录加载详细信息（就是“N”）。
          </li>
        </ul>

        
<p>
          这个问题会导致成百上千的 SQL 语句被执行。有时候，我们不希望产生这样的后果。
        </p>

        
<p>
          好消息是，MyBatis 能够对这样的查询进行延迟加载，因此可以将大量语句同时运行的开销分散开来。
          然而，如果你加载记录列表之后立刻就遍历列表以获取嵌套的数据，就会触发所有的延迟加载查询，性能可能会变得很糟糕。
        </p>

        
<p>
          所以还有另外一种方法。
        </p>

        </div>
<div class="section">
<h4><a name="a.E5.85.B3.E8.81.94.E7.9A.84.E5.B5.8C.E5.A5.97.E7.BB.93.E6.9E.9C.E6.98.A0.E5.B0.84"></a>关联的嵌套结果映射</h4>

        
<table border="0" class="table table-striped">
          <thead>
            
<tr class="a">
              
<th>属性</th>
              
<th>描述</th>
            </tr>
          </thead>
          <tbody>
            
<tr class="b">
              
<td><tt>resultMap</tt></td>
              
<td>
                结果映射的 ID，可以将此关联的嵌套结果集映射到一个合适的对象树中。
                它可以作为使用额外 select 语句的替代方案。它可以将多表连接操作的结果映射成一个单一的
                <tt>ResultSet</tt>。这样的 <tt>ResultSet</tt> 有部分数据是重复的。
                为了将结果集正确地映射到嵌套的对象树中, MyBatis
                允许你“串联”结果映射，以便解决嵌套结果集的问题。使用嵌套结果映射的一个例子在表格以后。
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>columnPrefix</tt></td>
              
<td>
				        当连接多个表时，你可能会不得不使用列别名来避免在 <tt>ResultSet</tt>
                中产生重复的列名。指定 columnPrefix 列名前缀允许你将带有这些前缀的列映射到一个外部的结果映射中。
                详细说明请参考后面的例子。
              </td>
            </tr>
            
<tr class="b">
              
<td><tt>notNullColumn</tt></td>
              
<td>
                默认情况下，在至少一个被映射到属性的列不为空时，子对象才会被创建。
                你可以在这个属性上指定非空的列来改变默认行为，指定后，Mybatis
                将只在这些列非空时才创建一个子对象。可以使用逗号分隔来指定多个列。默认值：未设置（unset）。
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>autoMapping</tt></td>
              
<td>
                如果设置这个属性，MyBatis 将会为本结果映射开启或者关闭自动映射。
                这个属性会覆盖全局的属性 autoMappingBehavior。注意，本属性对外部的结果映射无效，所以不能搭配
                <tt>select</tt> 或 <tt>resultMap</tt> 元素使用。默认值：未设置（unset）。
              </td>
            </tr>
          </tbody>
        </table>

        
<p>
          之前，你已经看到了一个非常复杂的嵌套关联的例子。
          下面的例子则是一个非常简单的例子，用于演示嵌套结果映射如何工作。
          现在我们将博客表和作者表连接在一起，而不是执行一个独立的查询语句，就像这样：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;select</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"selectBlog"</span><span class="pln"> </span><span class="atn">resultMap</span><span class="pun">=</span><span class="atv">"blogResult"</span><span class="tag">&gt;</span><span class="pln">
  select
    B.id            as blog_id,
    B.title         as blog_title,
    B.author_id     as blog_author_id,
    A.id            as author_id,
    A.username      as author_username,
    A.password      as author_password,
    A.email         as author_email,
    A.bio           as author_bio
  from Blog B left outer join Author A on B.author_id = A.id
  where B.id = #{id}
</span><span class="tag">&lt;/select&gt;</span></pre></div>

        
<p>
          注意查询中的连接，以及为确保结果能够拥有唯一且清晰的名字，我们设置的别名。
          这使得进行映射非常简单。现在我们可以映射这个结果：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;resultMap</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"blogResult"</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"Blog"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"blog_id"</span><span class="pln"> </span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"title"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"blog_title"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;association</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"author"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"blog_author_id"</span><span class="pln"> </span><span class="atn">javaType</span><span class="pun">=</span><span class="atv">"Author"</span><span class="pln"> </span><span class="atn">resultMap</span><span class="pun">=</span><span class="atv">"authorResult"</span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;/resultMap&gt;</span><span class="pln">

</span><span class="tag">&lt;resultMap</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"authorResult"</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"Author"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_id"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"username"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_username"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"password"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_password"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"email"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_email"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"bio"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_bio"</span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;/resultMap&gt;</span></pre></div>

        
<p>
          在上面的例子中，你可以看到，博客（Blog）作者（author）的关联元素委托名为
          “authorResult” 的结果映射来加载作者对象的实例。
        </p>

        
<p>
          <span class="important">非常重要</span>：
          id 元素在嵌套结果映射中扮演着非常重要的角色。你应该总是指定一个或多个可以唯一标识结果的属性。
          虽然，即使不指定这个属性，MyBatis 仍然可以工作，但是会产生严重的性能问题。
          只需要指定可以唯一标识结果的最少属性。显然，你可以选择主键（复合主键也可以）。
        </p>

        
<p>
          现在，上面的示例使用了外部的结果映射元素来映射关联。这使得 Author 的结果映射可以被重用。
          然而，如果你不打算重用它，或者你更喜欢将你所有的结果映射放在一个具有描述性的结果映射元素中。
          你可以直接将结果映射作为子元素嵌套在内。这里给出使用这种方式的等效例子：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;resultMap</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"blogResult"</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"Blog"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"blog_id"</span><span class="pln"> </span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"title"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"blog_title"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;association</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"author"</span><span class="pln"> </span><span class="atn">javaType</span><span class="pun">=</span><span class="atv">"Author"</span><span class="tag">&gt;</span><span class="pln">
    </span><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_id"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"username"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_username"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"password"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_password"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"email"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_email"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"bio"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_bio"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;/association&gt;</span><span class="pln">
</span><span class="tag">&lt;/resultMap&gt;</span></pre></div>

        
<p>
          那如果博客（blog）有一个共同作者（co-author）该怎么办？select 语句看起来会是这样的：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;select</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"selectBlog"</span><span class="pln"> </span><span class="atn">resultMap</span><span class="pun">=</span><span class="atv">"blogResult"</span><span class="tag">&gt;</span><span class="pln">
  select
    B.id            as blog_id,
    B.title         as blog_title,
    A.id            as author_id,
    A.username      as author_username,
    A.password      as author_password,
    A.email         as author_email,
    A.bio           as author_bio,
    CA.id           as co_author_id,
    CA.username     as co_author_username,
    CA.password     as co_author_password,
    CA.email        as co_author_email,
    CA.bio          as co_author_bio
  from Blog B
  left outer join Author A on B.author_id = A.id
  left outer join Author CA on B.co_author_id = CA.id
  where B.id = #{id}
</span><span class="tag">&lt;/select&gt;</span></pre></div>

        
<p>
          回忆一下，Author 的结果映射定义如下：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;resultMap</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"authorResult"</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"Author"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_id"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"username"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_username"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"password"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_password"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"email"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_email"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"bio"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_bio"</span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;/resultMap&gt;</span></pre></div>

        
<p>
          由于结果中的列名与结果映射中的列名不同。你需要指定 <tt>columnPrefix</tt>
          以便重复使用该结果映射来映射 co-author 的结果。
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;resultMap</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"blogResult"</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"Blog"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"blog_id"</span><span class="pln"> </span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"title"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"blog_title"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;association</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"author"</span><span class="pln">
    </span><span class="atn">resultMap</span><span class="pun">=</span><span class="atv">"authorResult"</span><span class="pln"> </span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;association</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"coAuthor"</span><span class="pln">
    </span><span class="atn">resultMap</span><span class="pun">=</span><span class="atv">"authorResult"</span><span class="pln">
    </span><span class="atn">columnPrefix</span><span class="pun">=</span><span class="atv">"co_"</span><span class="pln"> </span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;/resultMap&gt;</span></pre></div>

        </div>
<div class="section">
<h4><a name="a.E5.85.B3.E8.81.94.E7.9A.84.E5.A4.9A.E7.BB.93.E6.9E.9C.E9.9B.86.EF.BC.88ResultSet.EF.BC.89"></a>关联的多结果集（ResultSet）</h4>

        
<table border="0" class="table table-striped">
          <thead>
            
<tr class="a">
              
<th>属性</th>
              
<th>描述</th>
            </tr>
          </thead>
          <tbody>
            
<tr class="b">
              
<td><tt>column</tt></td>
              
<td>
                当使用多个结果集时，该属性指定结果集中用于与 <tt>foreignColumn</tt>
                匹配的列（多个列名以逗号隔开），以识别关系中的父类型与子类型。
              </td>
            </tr>
            
<tr class="a">
              
<td><tt>foreignColumn</tt></td>
              
<td>
                指定外键对应的列名，指定的列将与父类型中 <tt>column</tt> 的给出的列进行匹配。
              </td>
            </tr>
            
<tr class="b">
              
<td><tt>resultSet</tt></td>
              
<td>
                指定用于加载复杂类型的结果集名字。
              </td>
            </tr>
          </tbody>
        </table>

        
<p>从版本 3.2.3 开始，MyBatis 提供了另一种解决 N+1 查询问题的方法。</p>

        
<p>
          某些数据库允许存储过程返回多个结果集，或一次性执行多个语句，每个语句返回一个结果集。
          我们可以利用这个特性，在不使用连接的情况下，只访问数据库一次就能获得相关数据。
        </p>

        
<p>在例子中，存储过程执行下面的查询并返回两个结果集。第一个结果集会返回博客（Blog）的结果，第二个则返回作者（Author）的结果。</p>

        
<div class="source"><pre class="prettyprint"><span class="pln">SELECT </span><span class="pun">*</span><span class="pln"> FROM BLOG WHERE ID </span><span class="pun">=</span><span class="pln"> </span><span class="com">#{id}</span><span class="pln">

SELECT </span><span class="pun">*</span><span class="pln"> FROM AUTHOR WHERE ID </span><span class="pun">=</span><span class="pln"> </span><span class="com">#{id}</span></pre></div>

        
<p>在映射语句中，必须通过 <tt>resultSets</tt> 属性为每个结果集指定一个名字，多个名字使用逗号隔开。</p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;select</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"selectBlog"</span><span class="pln"> </span><span class="atn">resultSets</span><span class="pun">=</span><span class="atv">"blogs,authors"</span><span class="pln"> </span><span class="atn">resultMap</span><span class="pun">=</span><span class="atv">"blogResult"</span><span class="pln"> </span><span class="atn">statementType</span><span class="pun">=</span><span class="atv">"CALLABLE"</span><span class="tag">&gt;</span><span class="pln">
  {call getBlogsAndAuthors(#{id,jdbcType=INTEGER,mode=IN})}
</span><span class="tag">&lt;/select&gt;</span></pre></div>

        
<p>
          现在我们可以指定使用 “authors” 结果集的数据来填充 “author” 关联：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;resultMap</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"blogResult"</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"Blog"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"title"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"title"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;association</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"author"</span><span class="pln"> </span><span class="atn">javaType</span><span class="pun">=</span><span class="atv">"Author"</span><span class="pln"> </span><span class="atn">resultSet</span><span class="pun">=</span><span class="atv">"authors"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_id"</span><span class="pln"> </span><span class="atn">foreignColumn</span><span class="pun">=</span><span class="atv">"id"</span><span class="tag">&gt;</span><span class="pln">
    </span><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"id"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"username"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"username"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"password"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"password"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"email"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"email"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"bio"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"bio"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;/association&gt;</span><span class="pln">
</span><span class="tag">&lt;/resultMap&gt;</span></pre></div>

        
<p>
          你已经在上面看到了如何处理“有一个”类型的关联。但是该怎么处理“有很多个”类型的关联呢？这就是我们接下来要介绍的。
        </p>

        </div>
<div class="section">
<h4><a name="a.E9.9B.86.E5.90.88"></a>集合</h4>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;collection</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"posts"</span><span class="pln"> </span><span class="atn">ofType</span><span class="pun">=</span><span class="atv">"domain.blog.Post"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"post_id"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"subject"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"post_subject"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"body"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"post_body"</span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;/collection&gt;</span></pre></div>

        
<p>
          集合元素和关联元素几乎是一样的，它们相似的程度之高，以致于没有必要再介绍集合元素的相似部分。
          所以让我们来关注它们的不同之处吧。
        </p>

        
<p>
          我们来继续上面的示例，一个博客（Blog）只有一个作者（Author)。但一个博客有很多文章（Post)。
          在博客类中，这可以用下面的写法来表示：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="kwd">private</span><span class="pln"> </span><span class="typ">List</span><span class="pun">&lt;</span><span class="typ">Post</span><span class="pun">&gt;</span><span class="pln"> posts</span><span class="pun">;</span></pre></div>

        
<p>
          要像上面这样，映射嵌套结果集合到一个 List 中，可以使用集合元素。
          和关联元素一样，我们可以使用嵌套 Select 查询，或基于连接的嵌套结果映射集合。
        </p>

        </div>
<div class="section">
<h4><a name="a.E9.9B.86.E5.90.88.E7.9A.84.E5.B5.8C.E5.A5.97_Select_.E6.9F.A5.E8.AF.A2"></a>集合的嵌套 Select 查询</h4>

        
<p>
          首先，让我们看看如何使用嵌套 Select 查询来为博客加载文章。
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;resultMap</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"blogResult"</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"Blog"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;collection</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"posts"</span><span class="pln"> </span><span class="atn">javaType</span><span class="pun">=</span><span class="atv">"ArrayList"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">ofType</span><span class="pun">=</span><span class="atv">"Post"</span><span class="pln"> </span><span class="atn">select</span><span class="pun">=</span><span class="atv">"selectPostsForBlog"</span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;/resultMap&gt;</span><span class="pln">

</span><span class="tag">&lt;select</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"selectBlog"</span><span class="pln"> </span><span class="atn">resultMap</span><span class="pun">=</span><span class="atv">"blogResult"</span><span class="tag">&gt;</span><span class="pln">
  SELECT * FROM BLOG WHERE ID = #{id}
</span><span class="tag">&lt;/select&gt;</span><span class="pln">

</span><span class="tag">&lt;select</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"selectPostsForBlog"</span><span class="pln"> </span><span class="atn">resultType</span><span class="pun">=</span><span class="atv">"Post"</span><span class="tag">&gt;</span><span class="pln">
  SELECT * FROM POST WHERE BLOG_ID = #{id}
</span><span class="tag">&lt;/select&gt;</span></pre></div>

        
<p>
          你可能会立刻注意到几个不同，但大部分都和我们上面学习过的关联元素非常相似。
          首先，你会注意到我们使用的是集合元素。
          接下来你会注意到有一个新的 “ofType” 属性。这个属性非常重要，它用来将
          JavaBean（或字段）属性的类型和集合存储的类型区分开来。
          所以你可以按照下面这样来阅读映射：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;collection</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"posts"</span><span class="pln"> </span><span class="atn">javaType</span><span class="pun">=</span><span class="atv">"ArrayList"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">ofType</span><span class="pun">=</span><span class="atv">"Post"</span><span class="pln"> </span><span class="atn">select</span><span class="pun">=</span><span class="atv">"selectPostsForBlog"</span><span class="tag">/&gt;</span></pre></div>

        
<p>
          <span class="important">读作</span>： “posts 是一个存储 Post 的 ArrayList 集合”
        </p>

        
<p>
          在一般情况下，MyBatis 可以推断 javaType 属性，因此并不需要填写。所以很多时候你可以简略成：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;collection</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"posts"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">ofType</span><span class="pun">=</span><span class="atv">"Post"</span><span class="pln"> </span><span class="atn">select</span><span class="pun">=</span><span class="atv">"selectPostsForBlog"</span><span class="tag">/&gt;</span></pre></div>

        </div>
<div class="section">
<h4><a name="a.E9.9B.86.E5.90.88.E7.9A.84.E5.B5.8C.E5.A5.97.E7.BB.93.E6.9E.9C.E6.98.A0.E5.B0.84"></a>集合的嵌套结果映射</h4>

        
<p>
          现在你可能已经猜到了集合的嵌套结果映射是怎样工作的——除了新增的 “ofType” 属性，它和关联的完全相同。
        </p>

        
<p>首先, 让我们看看对应的 SQL 语句：</p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;select</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"selectBlog"</span><span class="pln"> </span><span class="atn">resultMap</span><span class="pun">=</span><span class="atv">"blogResult"</span><span class="tag">&gt;</span><span class="pln">
  select
  B.id as blog_id,
  B.title as blog_title,
  B.author_id as blog_author_id,
  P.id as post_id,
  P.subject as post_subject,
  P.body as post_body,
  from Blog B
  left outer join Post P on B.id = P.blog_id
  where B.id = #{id}
</span><span class="tag">&lt;/select&gt;</span></pre></div>

        
<p>
          我们再次连接了博客表和文章表，并且为每一列都赋予了一个有意义的别名，以便映射保持简单。
          要映射博客里面的文章集合，就这么简单：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;resultMap</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"blogResult"</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"Blog"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"blog_id"</span><span class="pln"> </span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"title"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"blog_title"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;collection</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"posts"</span><span class="pln"> </span><span class="atn">ofType</span><span class="pun">=</span><span class="atv">"Post"</span><span class="tag">&gt;</span><span class="pln">
    </span><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"post_id"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"subject"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"post_subject"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"body"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"post_body"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;/collection&gt;</span><span class="pln">
</span><span class="tag">&lt;/resultMap&gt;</span></pre></div>

        
<p>
          再提醒一次，要记得上面 id 元素的重要性，如果你不记得了，请阅读关联部分的相关部分。
        </p>

        
<p>
          如果你喜欢更详略的、可重用的结果映射，你可以使用下面的等价形式：
        </p>
        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;resultMap</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"blogResult"</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"Blog"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"blog_id"</span><span class="pln"> </span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"title"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"blog_title"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;collection</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"posts"</span><span class="pln"> </span><span class="atn">ofType</span><span class="pun">=</span><span class="atv">"Post"</span><span class="pln"> </span><span class="atn">resultMap</span><span class="pun">=</span><span class="atv">"blogPostResult"</span><span class="pln"> </span><span class="atn">columnPrefix</span><span class="pun">=</span><span class="atv">"post_"</span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;/resultMap&gt;</span><span class="pln">

</span><span class="tag">&lt;resultMap</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"blogPostResult"</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"Post"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"id"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"subject"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"subject"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"body"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"body"</span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;/resultMap&gt;</span></pre></div>

        </div>
<div class="section">
<h4><a name="a.E9.9B.86.E5.90.88.E7.9A.84.E5.A4.9A.E7.BB.93.E6.9E.9C.E9.9B.86.EF.BC.88ResultSet.EF.BC.89"></a>集合的多结果集（ResultSet）</h4>

        
<p>
          像关联元素那样，我们可以通过执行存储过程实现，它会执行两个查询并返回两个结果集，一个是博客的结果集，另一个是文章的结果集：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="pln">SELECT </span><span class="pun">*</span><span class="pln"> FROM BLOG WHERE ID </span><span class="pun">=</span><span class="pln"> </span><span class="com">#{id}</span><span class="pln">

SELECT </span><span class="pun">*</span><span class="pln"> FROM POST WHERE BLOG_ID </span><span class="pun">=</span><span class="pln"> </span><span class="com">#{id}</span></pre></div>

        
<p>在映射语句中，必须通过 <tt>resultSets</tt> 属性为每个结果集指定一个名字，多个名字使用逗号隔开。</p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;select</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"selectBlog"</span><span class="pln"> </span><span class="atn">resultSets</span><span class="pun">=</span><span class="atv">"blogs,posts"</span><span class="pln"> </span><span class="atn">resultMap</span><span class="pun">=</span><span class="atv">"blogResult"</span><span class="tag">&gt;</span><span class="pln">
  {call getBlogsAndPosts(#{id,jdbcType=INTEGER,mode=IN})}
</span><span class="tag">&lt;/select&gt;</span></pre></div>

        
<p>我们指定 “posts” 集合将会使用存储在 “posts” 结果集中的数据进行填充： </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;resultMap</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"blogResult"</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"Blog"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"title"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"title"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;collection</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"posts"</span><span class="pln"> </span><span class="atn">ofType</span><span class="pun">=</span><span class="atv">"Post"</span><span class="pln"> </span><span class="atn">resultSet</span><span class="pun">=</span><span class="atv">"posts"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">foreignColumn</span><span class="pun">=</span><span class="atv">"blog_id"</span><span class="tag">&gt;</span><span class="pln">
    </span><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"id"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"subject"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"subject"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"body"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"body"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;/collection&gt;</span><span class="pln">
</span><span class="tag">&lt;/resultMap&gt;</span></pre></div>

        
<p>
          <span class="label important">注意</span>
          对关联或集合的映射，并没有深度、广度或组合上的要求。但在映射时要留意性能问题。
          在探索最佳实践的过程中，应用的单元测试和性能测试会是你的好帮手。
          而 MyBatis 的好处在于，可以在不对你的代码引入重大变更（如果有）的情况下，允许你之后改变你的想法。
        </p>

        
<p>
          高级关联和集合映射是一个深度话题。文档的介绍只能到此为止。配合少许的实践，你会很快了解全部的用法。
        </p>

        </div>
<div class="section">
<h4><a name="a.E9.89.B4.E5.88.AB.E5.99.A8"></a>鉴别器</h4>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;discriminator</span><span class="pln"> </span><span class="atn">javaType</span><span class="pun">=</span><span class="atv">"int"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"draft"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;case</span><span class="pln"> </span><span class="atn">value</span><span class="pun">=</span><span class="atv">"1"</span><span class="pln"> </span><span class="atn">resultType</span><span class="pun">=</span><span class="atv">"DraftPost"</span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;/discriminator&gt;</span></pre></div>

        
<p>
          有时候，一个数据库查询可能会返回多个不同的结果集（但总体上还是有一定的联系的）。
          鉴别器（discriminator）元素就是被设计来应对这种情况的，另外也能处理其它情况，例如类的继承层次结构。
          鉴别器的概念很好理解——它很像 Java 语言中的 switch 语句。
        </p>

        
<p>
          一个鉴别器的定义需要指定 column 和 javaType 属性。column 指定了 MyBatis 查询被比较值的地方。
          而 javaType 用来确保使用正确的相等测试（虽然很多情况下字符串的相等测试都可以工作）。例如：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;resultMap</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"vehicleResult"</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"Vehicle"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"vin"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"vin"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"year"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"year"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"make"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"make"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"model"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"model"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"color"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"color"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;discriminator</span><span class="pln"> </span><span class="atn">javaType</span><span class="pun">=</span><span class="atv">"int"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"vehicle_type"</span><span class="tag">&gt;</span><span class="pln">
    </span><span class="tag">&lt;case</span><span class="pln"> </span><span class="atn">value</span><span class="pun">=</span><span class="atv">"1"</span><span class="pln"> </span><span class="atn">resultMap</span><span class="pun">=</span><span class="atv">"carResult"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;case</span><span class="pln"> </span><span class="atn">value</span><span class="pun">=</span><span class="atv">"2"</span><span class="pln"> </span><span class="atn">resultMap</span><span class="pun">=</span><span class="atv">"truckResult"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;case</span><span class="pln"> </span><span class="atn">value</span><span class="pun">=</span><span class="atv">"3"</span><span class="pln"> </span><span class="atn">resultMap</span><span class="pun">=</span><span class="atv">"vanResult"</span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;case</span><span class="pln"> </span><span class="atn">value</span><span class="pun">=</span><span class="atv">"4"</span><span class="pln"> </span><span class="atn">resultMap</span><span class="pun">=</span><span class="atv">"suvResult"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;/discriminator&gt;</span><span class="pln">
</span><span class="tag">&lt;/resultMap&gt;</span></pre></div>

        
<p>
          在这个示例中，MyBatis 会从结果集中得到每条记录，然后比较它的 vehicle type 值。
          如果它匹配任意一个鉴别器的 case，就会使用这个 case 指定的结果映射。
          这个过程是互斥的，也就是说，剩余的结果映射将被忽略（除非它是扩展的，我们将在稍后讨论它）。
          如果不能匹配任何一个 case，MyBatis 就只会使用鉴别器块外定义的结果映射。
          所以，如果 carResult 的声明如下：
        </p>


        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;resultMap</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"carResult"</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"Car"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"doorCount"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"door_count"</span><span class="pln"> </span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;/resultMap&gt;</span></pre></div>

        
<p>
          那么只有 doorCount 属性会被加载。这是为了即使鉴别器的 case
          之间都能分为完全独立的一组，尽管和父结果映射可能没有什么关系。在上面的例子中，我们当然知道
          cars 和 vehicles 之间有关系，也就是 Car 是一个
          Vehicle。因此，我们希望剩余的属性也能被加载。而这只需要一个小修改。
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;resultMap</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"carResult"</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"Car"</span><span class="pln"> </span><span class="atn">extends</span><span class="pun">=</span><span class="atv">"vehicleResult"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"doorCount"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"door_count"</span><span class="pln"> </span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;/resultMap&gt;</span></pre></div>

        
<p>
          现在 vehicleResult 和 carResult 的属性都会被加载了。
        </p>

        
<p>
          可能有人又会觉得映射的外部定义有点太冗长了。
          因此，对于那些更喜欢简洁的映射风格的人来说，还有另一种语法可以选择。例如：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;resultMap</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"vehicleResult"</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"Vehicle"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;id</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"id"</span><span class="pln"> </span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"vin"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"vin"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"year"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"year"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"make"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"make"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"model"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"model"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"color"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"color"</span><span class="tag">/&gt;</span><span class="pln">
  </span><span class="tag">&lt;discriminator</span><span class="pln"> </span><span class="atn">javaType</span><span class="pun">=</span><span class="atv">"int"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"vehicle_type"</span><span class="tag">&gt;</span><span class="pln">
    </span><span class="tag">&lt;case</span><span class="pln"> </span><span class="atn">value</span><span class="pun">=</span><span class="atv">"1"</span><span class="pln"> </span><span class="atn">resultType</span><span class="pun">=</span><span class="atv">"carResult"</span><span class="tag">&gt;</span><span class="pln">
      </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"doorCount"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"door_count"</span><span class="pln"> </span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;/case&gt;</span><span class="pln">
    </span><span class="tag">&lt;case</span><span class="pln"> </span><span class="atn">value</span><span class="pun">=</span><span class="atv">"2"</span><span class="pln"> </span><span class="atn">resultType</span><span class="pun">=</span><span class="atv">"truckResult"</span><span class="tag">&gt;</span><span class="pln">
      </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"boxSize"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"box_size"</span><span class="pln"> </span><span class="tag">/&gt;</span><span class="pln">
      </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"extendedCab"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"extended_cab"</span><span class="pln"> </span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;/case&gt;</span><span class="pln">
    </span><span class="tag">&lt;case</span><span class="pln"> </span><span class="atn">value</span><span class="pun">=</span><span class="atv">"3"</span><span class="pln"> </span><span class="atn">resultType</span><span class="pun">=</span><span class="atv">"vanResult"</span><span class="tag">&gt;</span><span class="pln">
      </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"powerSlidingDoor"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"power_sliding_door"</span><span class="pln"> </span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;/case&gt;</span><span class="pln">
    </span><span class="tag">&lt;case</span><span class="pln"> </span><span class="atn">value</span><span class="pun">=</span><span class="atv">"4"</span><span class="pln"> </span><span class="atn">resultType</span><span class="pun">=</span><span class="atv">"suvResult"</span><span class="tag">&gt;</span><span class="pln">
      </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"allWheelDrive"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"all_wheel_drive"</span><span class="pln"> </span><span class="tag">/&gt;</span><span class="pln">
    </span><span class="tag">&lt;/case&gt;</span><span class="pln">
  </span><span class="tag">&lt;/discriminator&gt;</span><span class="pln">
</span><span class="tag">&lt;/resultMap&gt;</span></pre></div>

        
<p>
          <span class="label important">提示</span>
          请注意，这些都是结果映射，如果你完全不设置任何的 result 元素，MyBatis
          将为你自动匹配列和属性。所以上面的例子大多都要比实际的更复杂。
          这也表明，大多数数据库的复杂度都比较高，我们不太可能一直依赖于这种机制。
        </p>

      </div></div>

      <a name="Auto-mapping"></a>
<div class="section" id="Auto-mapping">
<h3><a name="a.E8.87.AA.E5.8A.A8.E6.98.A0.E5.B0.84"></a>自动映射</h3>

        
<p>
           正如你在前面一节看到的，在简单的场景下，MyBatis
           可以为你自动映射查询结果。但如果遇到复杂的场景，你需要构建一个结果映射。
           但是在本节中，你将看到，你可以混合使用这两种策略。让我们深入了解一下自动映射是怎样工作的。
        </p>

        
<p>
           当自动映射查询结果时，MyBatis 会获取结果中返回的列名并在 Java
           类中查找相同名字的属性（忽略大小写）。 这意味着如果发现了 <i>ID</i> 列和
           <i>id</i> 属性，MyBatis 会将列 <i>ID</i> 的值赋给 <i>id</i> 属性。
        </p>

        
<p>
           通常数据库列使用大写字母组成的单词命名，单词间用下划线分隔；而 Java
           属性一般遵循驼峰命名法约定。为了在这两种命名方式之间启用自动映射，需要将
           <tt>mapUnderscoreToCamelCase</tt> 设置为 true。
        </p>

        
<p>
           甚至在提供了结果映射后，自动映射也能工作。在这种情况下，对于每一个结果映射，在
           ResultSet 出现的列，如果没有设置手动映射，将被自动映射。在自动映射处理完毕后，再处理手动映射。
           在下面的例子中，<i>id</i> 和 <i>userName</i> 列将被自动映射，<i>hashed_password</i> 列将根据配置进行映射。
        </p>
        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;select</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"selectUsers"</span><span class="pln"> </span><span class="atn">resultMap</span><span class="pun">=</span><span class="atv">"userResultMap"</span><span class="tag">&gt;</span><span class="pln">
  select
    user_id             as "id",
    user_name           as "userName",
    hashed_password
  from some_table
  where id = #{id}
</span><span class="tag">&lt;/select&gt;</span></pre></div>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;resultMap</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"userResultMap"</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"User"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"password"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"hashed_password"</span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;/resultMap&gt;</span></pre></div>

        
<p>
          有三种自动映射等级：
        </p>

        
<ul>
          
<li>
            <tt>NONE</tt> - 禁用自动映射。仅对手动映射的属性进行映射。
          </li>
          
<li>
            <tt>PARTIAL</tt> - 对除在内部定义了嵌套结果映射（也就是连接的属性）以外的属性进行映射
          </li>
          
<li>
            <tt>FULL</tt> - 自动映射所有属性。
          </li>
        </ul>

        
<p>
          默认值是 <tt>PARTIAL</tt>，这是有原因的。当对连接查询的结果使用
          <tt>FULL</tt> 时，连接查询会在同一行中获取多个不同实体的数据，因此可能导致非预期的映射。
          下面的例子将展示这种风险：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;select</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"selectBlog"</span><span class="pln"> </span><span class="atn">resultMap</span><span class="pun">=</span><span class="atv">"blogResult"</span><span class="tag">&gt;</span><span class="pln">
  select
    B.id,
    B.title,
    A.username,
  from Blog B left outer join Author A on B.author_id = A.id
  where B.id = #{id}
</span><span class="tag">&lt;/select&gt;</span></pre></div>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;resultMap</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"blogResult"</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"Blog"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;association</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"author"</span><span class="pln"> </span><span class="atn">resultMap</span><span class="pun">=</span><span class="atv">"authorResult"</span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;/resultMap&gt;</span><span class="pln">

</span><span class="tag">&lt;resultMap</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"authorResult"</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"Author"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"username"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"author_username"</span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;/resultMap&gt;</span></pre></div>

        
<p>
          在该结果映射中，<i>Blog</i> 和 <i>Author</i> 均将被自动映射。但是注意
          <i>Author</i> 有一个 <i>id</i> 属性，在 ResultSet 中也有一个名为 <i>id</i>
          的列，所以 Author 的 id 将填入 Blog 的 id，这可不是你期望的行为。
          所以，要谨慎使用 <tt>FULL</tt>。
        </p>

        
<p>
          无论设置的自动映射等级是哪种，你都可以通过在结果映射上设置 <tt>autoMapping</tt>
          属性来为指定的结果映射设置启用/禁用自动映射。
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;resultMap</span><span class="pln"> </span><span class="atn">id</span><span class="pun">=</span><span class="atv">"userResultMap"</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"User"</span><span class="pln"> </span><span class="atn">autoMapping</span><span class="pun">=</span><span class="atv">"false"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;result</span><span class="pln"> </span><span class="atn">property</span><span class="pun">=</span><span class="atv">"password"</span><span class="pln"> </span><span class="atn">column</span><span class="pun">=</span><span class="atv">"hashed_password"</span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;/resultMap&gt;</span></pre></div>

      </div>

      <a name="cache"></a>
<div class="section" id="cache">
<h3><a name="a.E7.BC.93.E5.AD.98"></a>缓存</h3>
        
<p>
          MyBatis 内置了一个强大的事务性查询缓存机制，它可以非常方便地配置和定制。
          为了使它更加强大而且易于配置，我们对 MyBatis 3 中的缓存实现进行了许多改进。
        </p>

        
<p>
          默认情况下，只启用了本地的会话缓存，它仅仅对一个会话中的数据进行缓存。
          要启用全局的二级缓存，只需要在你的 SQL 映射文件中添加一行：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;cache/&gt;</span></pre></div>

        
<p>
          基本上就是这样。这个简单语句的效果如下:
        </p>

        
<ul>
          
<li>映射语句文件中的所有 select 语句的结果将会被缓存。</li>
          
<li>映射语句文件中的所有 insert、update 和 delete 语句会刷新缓存。</li>
          
<li>缓存会使用最近最少使用算法（LRU, Least Recently Used）算法来清除不需要的缓存。</li>
          
<li>缓存不会定时进行刷新（也就是说，没有刷新间隔）。</li>
          
<li>缓存会保存列表或对象（无论查询方法返回哪种）的 1024 个引用。</li>
          
<li>缓存会被视为读/写缓存，这意味着获取到的对象并不是共享的，可以安全地被调用者修改，而不干扰其他调用者或线程所做的潜在修改。</li>
        </ul>

        
<p>
          <span class="label important">提示</span>
          缓存只作用于 cache 标签所在的映射文件中的语句。如果你混合使用 Java API 和 XML
          映射文件，在共用接口中的语句将不会被默认缓存。你需要使用 @CacheNamespaceRef 注解指定缓存作用域。
        </p>

        
<p>
          这些属性可以通过 cache 元素的属性来修改。比如：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;cache</span><span class="pln">
  </span><span class="atn">eviction</span><span class="pun">=</span><span class="atv">"FIFO"</span><span class="pln">
  </span><span class="atn">flushInterval</span><span class="pun">=</span><span class="atv">"60000"</span><span class="pln">
  </span><span class="atn">size</span><span class="pun">=</span><span class="atv">"512"</span><span class="pln">
  </span><span class="atn">readOnly</span><span class="pun">=</span><span class="atv">"true"</span><span class="tag">/&gt;</span></pre></div>

        
<p>
          这个更高级的配置创建了一个 FIFO 缓存，每隔 60 秒刷新，最多可以存储结果对象或列表的 512
          个引用，而且返回的对象被认为是只读的，因此对它们进行修改可能会在不同线程中的调用者产生冲突。
        </p>

        
<p>
          可用的清除策略有：
        </p>

        
<ul>
          
<li>
            <tt>LRU</tt> – 最近最少使用：移除最长时间不被使用的对象。
          </li>
          
<li><tt>FIFO</tt> – 先进先出：按对象进入缓存的顺序来移除它们。
          </li>
          
<li>
            <tt>SOFT</tt> – 软引用：基于垃圾回收器状态和软引用规则移除对象。
          </li>
          
<li>
            <tt>WEAK</tt> – 弱引用：更积极地基于垃圾收集器状态和弱引用规则移除对象。
          </li>
        </ul>

        
<p>默认的清除策略是 LRU。</p>

        
<p>
          flushInterval（刷新间隔）属性可以被设置为任意的正整数，设置的值应该是一个以毫秒为单位的合理时间量。
          默认情况是不设置，也就是没有刷新间隔，缓存仅仅会在调用语句时刷新。
        </p>

        
<p>
          size（引用数目）属性可以被设置为任意正整数，要注意欲缓存对象的大小和运行环境中可用的内存资源。默认值是 1024。
        </p>

        
<p>
          readOnly（只读）属性可以被设置为 true 或 false。只读的缓存会给所有调用者返回缓存对象的相同实例。
          因此这些对象不能被修改。这就提供了可观的性能提升。而可读写的缓存会（通过序列化）返回缓存对象的拷贝。
          速度上会慢一些，但是更安全，因此默认值是 false。
        </p>

        
<p>
          <span class="label important">提示</span>
          二级缓存是事务性的。这意味着，当 SqlSession 完成并提交时，或是完成并回滚，但没有执行
          flushCache=true 的 insert/delete/update 语句时，缓存会获得更新。
        </p>

        
<div class="section">
<h4><a name="a.E4.BD.BF.E7.94.A8.E8.87.AA.E5.AE.9A.E4.B9.89.E7.BC.93.E5.AD.98"></a>使用自定义缓存</h4>

        
<p>
          除了上述自定义缓存的方式，你也可以通过实现你自己的缓存，或为其他第三方缓存方案创建适配器，来完全覆盖缓存行为。
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;cache</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"com.domain.something.MyCustomCache"</span><span class="tag">/&gt;</span></pre></div>

        
<p>
          这个示例展示了如何使用一个自定义的缓存实现。type 属性指定的类必须实现
          org.mybatis.cache.Cache 接口，且提供一个接受 String 参数作为 id 的构造器。
          这个接口是 MyBatis 框架中许多复杂的接口之一，但是行为却非常简单。
        </p>

        
<div class="source"><pre class="prettyprint"><span class="kwd">public</span><span class="pln"> </span><span class="kwd">interface</span><span class="pln"> </span><span class="typ">Cache</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
  </span><span class="typ">String</span><span class="pln"> getId</span><span class="pun">();</span><span class="pln">
  </span><span class="kwd">int</span><span class="pln"> getSize</span><span class="pun">();</span><span class="pln">
  </span><span class="kwd">void</span><span class="pln"> putObject</span><span class="pun">(</span><span class="typ">Object</span><span class="pln"> key</span><span class="pun">,</span><span class="pln"> </span><span class="typ">Object</span><span class="pln"> value</span><span class="pun">);</span><span class="pln">
  </span><span class="typ">Object</span><span class="pln"> getObject</span><span class="pun">(</span><span class="typ">Object</span><span class="pln"> key</span><span class="pun">);</span><span class="pln">
  </span><span class="kwd">boolean</span><span class="pln"> hasKey</span><span class="pun">(</span><span class="typ">Object</span><span class="pln"> key</span><span class="pun">);</span><span class="pln">
  </span><span class="typ">Object</span><span class="pln"> removeObject</span><span class="pun">(</span><span class="typ">Object</span><span class="pln"> key</span><span class="pun">);</span><span class="pln">
  </span><span class="kwd">void</span><span class="pln"> clear</span><span class="pun">();</span><span class="pln">
</span><span class="pun">}</span></pre></div>

        
<p>
          为了对你的缓存进行配置，只需要简单地在你的缓存实现中添加公有的 JavaBean 属性，然后通过
          cache 元素传递属性值，例如，下面的例子将在你的缓存实现上调用一个名为
          <tt>setCacheFile(String file)</tt> 的方法：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;cache</span><span class="pln"> </span><span class="atn">type</span><span class="pun">=</span><span class="atv">"com.domain.something.MyCustomCache"</span><span class="tag">&gt;</span><span class="pln">
  </span><span class="tag">&lt;property</span><span class="pln"> </span><span class="atn">name</span><span class="pun">=</span><span class="atv">"cacheFile"</span><span class="pln"> </span><span class="atn">value</span><span class="pun">=</span><span class="atv">"/tmp/my-custom-cache.tmp"</span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;/cache&gt;</span></pre></div>

        
<p>
          你可以使用所有简单类型作为 JavaBean 属性的类型，MyBatis 会进行转换。
          你也可以使用占位符（如 <tt>${cache.file}</tt>），以便替换成在<a href="configuration.html#properties">配置文件属性</a>中定义的值。
        </p>

        
<p>
          从版本 3.4.2 开始，MyBatis 已经支持在所有属性设置完毕之后，调用一个初始化方法。
          如果想要使用这个特性，请在你的自定义缓存类里实现
          <tt>org.apache.ibatis.builder.InitializingObject</tt> 接口。
        </p>

        
<div class="source"><pre class="prettyprint"><span class="kwd">public</span><span class="pln"> </span><span class="kwd">interface</span><span class="pln"> </span><span class="typ">InitializingObject</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
  </span><span class="kwd">void</span><span class="pln"> initialize</span><span class="pun">()</span><span class="pln"> </span><span class="kwd">throws</span><span class="pln"> </span><span class="typ">Exception</span><span class="pun">;</span><span class="pln">
</span><span class="pun">}</span></pre></div>

        
<p><span class="label important">提示</span>
          上一节中对缓存的配置（如清除策略、可读或可读写等），不能应用于自定义缓存。
        </p>

        
<p>
          请注意，缓存的配置和缓存实例会被绑定到 SQL 映射文件的命名空间中。
          因此，同一命名空间中的所有语句和缓存将通过命名空间绑定在一起。
          每条语句可以自定义与缓存交互的方式，或将它们完全排除于缓存之外，这可以通过在每条语句上使用两个简单属性来达成。
          默认情况下，语句会这样来配置：
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;select</span><span class="pln"> ... </span><span class="atn">flushCache</span><span class="pun">=</span><span class="atv">"false"</span><span class="pln"> </span><span class="atn">useCache</span><span class="pun">=</span><span class="atv">"true"</span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;insert</span><span class="pln"> ... </span><span class="atn">flushCache</span><span class="pun">=</span><span class="atv">"true"</span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;update</span><span class="pln"> ... </span><span class="atn">flushCache</span><span class="pun">=</span><span class="atv">"true"</span><span class="tag">/&gt;</span><span class="pln">
</span><span class="tag">&lt;delete</span><span class="pln"> ... </span><span class="atn">flushCache</span><span class="pun">=</span><span class="atv">"true"</span><span class="tag">/&gt;</span></pre></div>

        
<p>
          鉴于这是默认行为，显然你永远不应该以这样的方式显式配置一条语句。但如果你想改变默认的行为，只需要设置 flushCache 和 useCache 属性。比如，某些情况下你可能希望特定 select 语句的结果排除于缓存之外，或希望一条 select 语句清空缓存。类似地，你可能希望某些 update 语句执行时不要刷新缓存。
        </p>

          </div>
<div class="section">
<h4><a name="cache-ref"></a>cache-ref</h4>
        
<p>
          回想一下上一节的内容，对某一命名空间的语句，只会使用该命名空间的缓存进行缓存或刷新。
          但你可能会想要在多个命名空间中共享相同的缓存配置和实例。要实现这种需求，你可以使用
          cache-ref 元素来引用另一个缓存。
        </p>

        
<div class="source"><pre class="prettyprint"><span class="tag">&lt;cache-ref</span><span class="pln"> </span><span class="atn">namespace</span><span class="pun">=</span><span class="atv">"com.someone.application.data.SomeMapper"</span><span class="tag">/&gt;</span></pre></div>
      </div></div>
    </div>
