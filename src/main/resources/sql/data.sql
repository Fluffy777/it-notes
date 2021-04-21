/**
 * Початкові дані
 */
INSERT INTO roles (role_id, name) VALUES (roles_seq.nextval/*1*/, 'ADMIN');
INSERT INTO roles (role_id, name) VALUES (roles_seq.nextval/*2*/, 'USER');

-- email: john_doe@example.com
-- password: checkPassword123
INSERT INTO users (user_id, first_name, last_name, gender, email, password, rday, bday, description, address)
VALUES (users_seq.nextval/*1*/, 'John', 'Doe', 'M', 'john_doe@example.com', '$2y$10$eKPBoe7825jzrYXiZnWm5OPr.n2J2iR.4qOiBzYiPj.7K/htKSv.6', TO_DATE('18/04/2021', 'DD/MM/YYYY'), TO_DATE('10/12/1993', 'DD/MM/YYYY'), 'Admin', 'Sumy, Ukraine');

-- email: jane_doe@example.com
-- password: otherPassword123
INSERT INTO users (user_id, first_name, last_name, gender, email, password, rday, bday, description, address)
VALUES (users_seq.nextval/*2*/, 'Jane', 'Doe', 'F', 'jane_doe@example.com', '$2y$10$j29nTf.rjVRBxENkp5gfZewL5Wj5PecEwMReVCiWO9IHuGVL9yGa6', TO_DATE('16/04/2021', 'DD/MM/YYYY'), TO_DATE('12/12/2002', 'DD/MM/YYYY'), 'User', 'Kyiv, Ukraine');

-- email: jason_eagle@example.com
-- password: 07wsWsKfwD
INSERT INTO users (user_id, first_name, last_name, gender, email, password, rday, bday, description, address)
VALUES (users_seq.nextval/*3*/, 'Jason', 'Eagle', 'M', 'jason_eagle@example.com', '$2y$10$bufV3Xnx5yiUKJbJSjXuYeBRjiYjLooN3jCq5swF1UI82nLvkk8xG', TO_DATE('28/11/2020', 'DD/MM/YYYY'), TO_DATE('16/05/1985', 'DD/MM/YYYY'), 'User', 'Zuhres, Ukraine');

-- email: fleece_marigold@example.com
-- password: npmnAJzY3o
INSERT INTO users (user_id, first_name, last_name, gender, email, password, rday, bday, description, address)
VALUES (users_seq.nextval/*4*/, 'Fleece', 'Marigold', 'F', 'fleece_marigold@example.com', '$2y$10$x7/pAL9Q8pxVDDCafyh5Z.W0Amz5Z8gToTEb6tYQEtsrrK86IVjXK', TO_DATE('06/04/2013', 'DD/MM/YYYY'), TO_DATE('04/10/1996', 'DD/MM/YYYY'), 'Blonde-locked. Leader. No followers.', 'Sudova Vyshnia, Ukraine');

-- email: jackson_pot@example.com
-- password: 3oY2Iy8XmA
INSERT INTO users (user_id, first_name, last_name, gender, email, password, rday, bday, description, address)
VALUES (users_seq.nextval/*5*/, 'Jackson', 'Pot', 'M', 'jackson_pot@example.com', '$2y$10$uPcS2aS3gNuA.2tnWMjDpuL/dJGbQRoCDzqz8LFayK4ktVde8zA2K', TO_DATE('15/08/2013', 'DD/MM/YYYY'), TO_DATE('23/04/2000', 'DD/MM/YYYY'), 'Gambling addict.', 'Kaniv, Ukraine');

-- email: joss_sticks@example.com
-- password: OHKMaf8WqP
INSERT INTO users (user_id, first_name, last_name, gender, email, password, rday, bday, description, address)
VALUES (users_seq.nextval/*6*/, 'Joss', 'Sticks', 'M', 'joss_sticks@example.com', '$2y$10$ApzZnLhjJRexevrahH/1FeZ8pdalVHCj941l7HeTQZUWgnvLj3due', TO_DATE('29/01/2019', 'DD/MM/YYYY'), TO_DATE('15/05/1989', 'DD/MM/YYYY'), 'Non-smoker', 'Kadiyivka, Ukraine');

-- email: ruby_rails@example.com
-- password: oTd77WUiby
INSERT INTO users (user_id, first_name, last_name, gender, email, password, rday, bday, description, address)
VALUES (users_seq.nextval/*7*/, 'Ruby', 'Rails', 'F', 'ruby_rails@example.com', '$2y$10$zSa3fQoYcefr22dwq9/SAuhM.viqOXScMo7HDGjnfIvRbWcEPHVE2', TO_DATE('19/10/2016', 'DD/MM/YYYY'), TO_DATE('01/03/1988', 'DD/MM/YYYY'), 'Tech-enthusiast. Feminist.', 'Hertsa, Ukraine');

-- email: dianne_ameter@example.com
-- password: k0tutyHNNW
INSERT INTO users (user_id, first_name, last_name, gender, email, password, rday, bday, description, address)
VALUES (users_seq.nextval/*8*/, 'Dianne', 'Ameter', 'F', 'dianne_ameter@example.com', '$2y$10$hfpuiznAAyVniyU/vv0Nl.r8JZhtBhLQ5ZMnuMH3Ig2UjG38HX/ke', TO_DATE('04/03/2018', 'DD/MM/YYYY'), TO_DATE('19/07/1987', 'DD/MM/YYYY'), 'Rotund. Sister of Sir Cumference.', 'Romny, Ukraine');

-- email: manuel_internetiquette@example.com
-- password: zpy9YBB7H4
INSERT INTO users (user_id, first_name, last_name, gender, email, password, rday, bday, description, address)
VALUES (users_seq.nextval/*9*/, 'Manuel', 'Internetiquette', 'M', 'manuel_internetiquette@example.com', '$2y$10$z827yQn6lT4d53ppjJ17FOCH6bsRv25i5RcihqgEcJE0rVO6YlVqy', TO_DATE('20/02/2018', 'DD/MM/YYYY'), TO_DATE('12/12/1993', 'DD/MM/YYYY'), 'Continental customer service rep. Exceptionally polite.', 'Novomoskovsk, Ukraine');

-- email: hugh_millie-yate@example.com
-- password: Vss1NCxINK
INSERT INTO users (user_id, first_name, last_name, gender, email, password, rday, bday, description, address)
VALUES (users_seq.nextval/*10*/, 'Hugh', 'Millie-Yate', 'M', 'hugh_millie-yate@example.com', '$2y$10$86A1Xhr4QCQnJIhw5Bvz3.t2ohSZ7aTW3teMzc6xHHawnyKd3NXHm', TO_DATE('08/02/2015', 'DD/MM/YYYY'), TO_DATE('07/12/1994', 'DD/MM/YYYY'), 'High self-esteem.', 'Liuboml, Ukraine');

-- email: nathaneal_down@example.com
-- password: xodu3JFn27
INSERT INTO users (user_id, first_name, last_name, gender, email, password, rday, bday, description, address)
VALUES (users_seq.nextval/*11*/, 'Nathaneal', 'Down', 'F', 'nathaneal_down@example.com', '$2y$10$QFnWo7tel5YA2x9P2UP4Tum7L2UXJjIqjsw473DByFfGDhpZciUwS', TO_DATE('04/03/2020', 'DD/MM/YYYY'), TO_DATE('01/06/1996', 'DD/MM/YYYY'), 'Knee pad knitter.', 'Yalta, Ukraine');

-- email: giles_posture@example.com
-- password: s3ol5pvTM7
INSERT INTO users (user_id, first_name, last_name, gender, email, password, rday, bday, description, address)
VALUES (users_seq.nextval/*12*/, 'Giles', 'Posture', 'M', 'giles_posture@example.com', '$2y$10$twJ6mMr05YUMSUg3TqXY0ubm0.OhwE4IxF.6KPfZpGFiW59r40fCq', TO_DATE('01/06/2019', 'DD/MM/YYYY'), TO_DATE('28/06/1998', 'DD/MM/YYYY'), 'Osteopath. Qualified yoga instructor. Hunchback.', 'Biliaivka, Ukraine');

-- email: bartholomew_shoe@example.com
-- password: 8xJ43mK2pm
INSERT INTO users (user_id, first_name, last_name, gender, email, password, rday, bday, description, address)
VALUES (users_seq.nextval/*13*/, 'Bartholomew', 'Shoe', 'M', 'bartholomew_shoe@example.com', '$2y$10$3Pd2CFOL.mWWA1rRKrUvnugnpja.MAwsrH/6/RUXactnUOq8U.ywm', TO_DATE('27/07/2016', 'DD/MM/YYYY'), TO_DATE('04/08/1995', 'DD/MM/YYYY'), 'Tall. Cumbersome. Tiny feet.', 'Tatarbunary, Ukraine');

-- email: jake_weary@example.com
-- password: BY6bZ3qCm3
INSERT INTO users (user_id, first_name, last_name, gender, email, password, rday, bday, description, address)
VALUES (users_seq.nextval/*14*/, 'Jake', 'Weary', 'M', 'jake_weary@example.com', '$2y$10$9qHMEK6rGt.er5rI.OX1wudZisbtdIARbGLZL/3n.d1oByL5GGxVC', TO_DATE('16/11/2013', 'DD/MM/YYYY'), TO_DATE('24/09/1987', 'DD/MM/YYYY'), 'Energetic. Cuts corners.', 'Alchevsk, Ukraine');

-- email: chaplain_mondover@example.com
-- password: bEa9fDjb3t
INSERT INTO users (user_id, first_name, last_name, gender, email, password, rday, bday, description, address)
VALUES (users_seq.nextval/*15*/, 'Chaplain', 'Mondover', 'M', 'chaplain_mondover@example.com', '$2y$10$gO/LbwpTnqbcDg671EN6x./NmlZo4xI8aTUBszWjOj7D4jfaFQROK', TO_DATE('23/03/2017', 'DD/MM/YYYY'), TO_DATE('04/02/1987', 'DD/MM/YYYY'), 'Church dweller.', 'Shpola, Ukraine');

INSERT INTO user_role VALUES (1 /*John Doe*/, 1 /*ADMIN*/);
INSERT INTO user_role VALUES (1 /*John Doe*/, 2 /*USER*/);

INSERT INTO user_role VALUES (2 /*Jane Doe*/, 2 /*USER*/);

INSERT INTO user_role VALUES (3 /*Jason Eagle*/, 2 /*USER*/);
INSERT INTO user_role VALUES (4 /*Fleece Marigold*/, 2 /*USER*/);
INSERT INTO user_role VALUES (5 /*Jackson Pot*/, 2 /*USER*/);
INSERT INTO user_role VALUES (6 /*Joss Sticks*/, 2 /*USER*/);

INSERT INTO user_role VALUES (7 /*Ruby Rails*/, 1 /*ADMIN*/);
INSERT INTO user_role VALUES (7 /*Ruby Rails*/, 2 /*USER*/);

INSERT INTO user_role VALUES (8 /*Dianne Ameter*/, 2 /*USER*/);
INSERT INTO user_role VALUES (9 /*Manuel Internetiquette*/, 2 /*USER*/);
INSERT INTO user_role VALUES (10 /*Hugh Millie-Yate*/, 2 /*USER*/);
INSERT INTO user_role VALUES (11 /*Nathaneal Down*/, 2 /*USER*/);
INSERT INTO user_role VALUES (12 /*Giles Posture*/, 2 /*USER*/);
INSERT INTO user_role VALUES (13 /*Bartholomew Shoe*/, 2 /*USER*/);

INSERT INTO user_role VALUES (14 /*Jake Weary*/, 1 /*ADMIN*/);
INSERT INTO user_role VALUES (14 /*Jake Weary*/, 2 /*USER*/);

INSERT INTO user_role VALUES (15 /*Chaplain Mondover*/, 2 /*USER*/);

INSERT INTO categories VALUES (categories_seq.nextval/*1*/, 'C++');
INSERT INTO categories VALUES (categories_seq.nextval/*2*/, 'Java');
INSERT INTO categories VALUES (categories_seq.nextval/*3*/, 'Oracle SQL');
INSERT INTO categories VALUES (categories_seq.nextval/*4*/, 'jQuery');
INSERT INTO categories VALUES (categories_seq.nextval/*5*/, 'Lua');

INSERT INTO articles VALUES (articles_seq.nextval/*1*/, 1/*C++*/, 1/*John Doe*/, 'Hello world application',
                             q'[#include <iostream>

int main() {
    std::cout << "Hello, world!" << std::endl;
    return 0;
}]', TO_DATE('10/01/2020', 'DD/MM/YYYY'), 30);

INSERT INTO articles VALUES (articles_seq.nextval/*2*/, 1/*C++*/, 1/*John Doe*/, 'Radix sort',
                             q'[#include <iostream>
using namespace std;

int getMax(int arr[], int n)
{
	int mx = arr[0];
	for (int i = 1; i < n; i++)
		if (arr[i] > mx)
			mx = arr[i];
	return mx;
}

void countSort(int arr[], int n, int exp)
{
	int* output = new int[n]; // output array
	int i, count[10] = { 0 };

	// Store count of occurrences in count[]
	for (i = 0; i < n; i++)
		count[(arr[i] / exp) % 10]++;

	// Change count[i] so that count[i] now contains actual
	//  position of this digit in output[]
	for (i = 1; i < 10; i++)
		count[i] += count[i - 1];

	// Build the output array
	for (i = n - 1; i >= 0; i--)
	{
		output[count[(arr[i] / exp) % 10] - 1] = arr[i];
		count[(arr[i] / exp) % 10]--;
	}

	// Copy the output array to arr[], so that arr[] now
	// contains sorted numbers according to current digit
	for (i = 0; i < n; i++)
		arr[i] = output[i];

	delete[] output;
}

void radixsort(int arr[], int n)
{
	// Find the maximum number to know number of digits
	int m = getMax(arr, n);

	// Do counting sort for every digit. Note that instead
	// of passing digit number, exp is passed. exp is 10^i
	// where i is current digit number
	for (int exp = 1; m / exp > 0; exp *= 10)
		countSort(arr, n, exp);
}

int main()
{
	setlocale(LC_ALL,"");

	int arr[9] {7,6,3,76,34,93,1250,500,400};
	radixsort(arr,9);

	system("pause");
	return 0;
}]', TO_DATE('11/01/2020', 'DD/MM/YYYY'), 25);

INSERT INTO articles VALUES (articles_seq.nextval/*3*/, 1/*C++*/, 1/*John Doe*/, 'Binary search',
                             q'[#include <bits/stdc++.h>
using namespace std;

// A recursive binary search function. It returns
// location of x in given array arr[l..r] is present,
// otherwise -1
int binarySearch(int arr[], int l, int r, int x)
{
    if (r >= l) {
        int mid = l + (r - l) / 2;

        // If the element is present at the middle
        // itself
        if (arr[mid] == x)
            return mid;

        // If element is smaller than mid, then
        // it can only be present in left subarray
        if (arr[mid] > x)
            return binarySearch(arr, l, mid - 1, x);

        // Else the element can only be present
        // in right subarray
        return binarySearch(arr, mid + 1, r, x);
    }

    // We reach here when element is not
    // present in array
    return -1;
}

int main(void)
{
    int arr[] = { 2, 3, 4, 10, 40 };
    int x = 10;
    int n = sizeof(arr) / sizeof(arr[0]);
    int result = binarySearch(arr, 0, n - 1, x);
    (result == -1) ? cout << "Element is not present in array"
                   : cout << "Element is present at index " << result;
    return 0;
}]', TO_DATE('02/02/2020', 'DD/MM/YYYY'), 50);


INSERT INTO articles VALUES (articles_seq.nextval/*4*/, 2/*Java*/, 1/*John Doe*/, 'JSP as views',
                             q'[@Bean
public InternalResourceViewResolver viewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/views/");
    viewResolver.setSuffix(".jsp");
    return viewResolver;
}]', TO_DATE('03/02/2020', 'DD/MM/YYYY'), 75);
COMMIT;

INSERT INTO articles VALUES (articles_seq.nextval/*5*/, 2/*Java*/, 7/*Ruby Rails*/, 'Hello world',
                             q'[class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}]', TO_DATE('10/02/2020', 'DD/MM/YYYY'), 10);
COMMIT;

INSERT INTO articles VALUES (articles_seq.nextval/*6*/, 2/*Java*/, 7/*Ruby Rails*/, 'Security configuration example',
                             q'[@Override
protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/login", "/signup").anonymous()
            .antMatchers("/profile", "/logout").authenticated()
        .and()
            .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/login/processing")
            .usernameParameter("email")
            .passwordParameter("password")
            .defaultSuccessUrl("/")
            .failureUrl("/login?error=true")
        .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
        .and()
            .csrf()
            .disable();
}]', TO_DATE('15/03/2020', 'DD/MM/YYYY'), 72);

INSERT INTO articles VALUES (articles_seq.nextval/*7*/, 2/*Java*/, 14/*Jake Weary*/, 'Simple controller',
                             q'[@Controller
public class PrimaryController {
    public PrimaryController() {
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}]', TO_DATE('20/03/2020', 'DD/MM/YYYY'), 100);

INSERT INTO articles VALUES (articles_seq.nextval/*8*/, 3/*Oracle SQL*/, 1/*John Doe*/, 'Schema cleaner',
                             q'[BEGIN
   FOR cur_rec IN (SELECT object_name, object_type
                     FROM user_objects
                    WHERE object_type IN
                             ('TABLE',
                              'VIEW',
                              'PACKAGE',
                              'PROCEDURE',
                              'FUNCTION',
                              'SEQUENCE',
                              'SYNONYM'
                             ))
   LOOP
      BEGIN
         IF cur_rec.object_type = 'TABLE'
         THEN
            EXECUTE IMMEDIATE    'DROP '
                              || cur_rec.object_type
                              || ' "'
                              || cur_rec.object_name
                              || '" CASCADE CONSTRAINTS';
         ELSE
            EXECUTE IMMEDIATE    'DROP '
                              || cur_rec.object_type
                              || ' "'
                              || cur_rec.object_name
                              || '"';
         END IF;
      EXCEPTION
         WHEN OTHERS
         THEN
            DBMS_OUTPUT.put_line (   'FAILED: DROP '
                                  || cur_rec.object_type
                                  || ' "'
                                  || cur_rec.object_name
                                  || '"'
                                 );
      END;
   END LOOP;
END;]', TO_DATE('01/05/2020', 'DD/MM/YYYY'), 90);

INSERT INTO articles VALUES (articles_seq.nextval/*9*/, 3/*Oracle SQL*/, 1/*John Doe*/, 'emp-dept',
                             q'[create table dept(
  deptno     number(2,0),
  dname      varchar2(14),
  loc        varchar2(13),
  constraint pk_dept primary key (deptno)
);

create table emp(
  empno    number(4,0),
  ename    varchar2(10),
  job      varchar2(9),
  mgr      number(4,0),
  hiredate date,
  sal      number(7,2),
  comm     number(7,2),
  deptno   number(2,0),
  constraint pk_emp primary key (empno),
  constraint fk_deptno foreign key (deptno) references dept (deptno)
);]', TO_DATE('20/03/2020', 'DD/MM/YYYY'), 67);

INSERT INTO articles VALUES (articles_seq.nextval/*10*/, 4/*jQuery*/, 7/*Ruby Rails*/, '$.each',
                             q'[$.each([ 52, 97 ], function( index, value ) {
  alert( index + ": " + value );
});]', TO_DATE('12/10/2020', 'DD/MM/YYYY'), 35);

INSERT INTO articles VALUES (articles_seq.nextval/*11*/, 4/*jQuery*/, 14/*Jake Weary*/, '$.attr',
                             q'[$( "#greatphoto" ).attr( "alt", "Beijing Brush Seller" );
$( "#greatphoto" ).attr({
  alt: "Beijing Brush Seller",
  title: "photo by Kelly Clark"
});
$( "#greatphoto" ).attr( "title", function( i, val ) {
  return val + " - photo by Kelly Clark";
});]', TO_DATE('09/11/2020', 'DD/MM/YYYY'), 49);

INSERT INTO articles VALUES (articles_seq.nextval/*12*/, 4/*jQuery*/, 14/*Jake Weary*/, '$.ajax',
                             q'[$.ajax({
    url: baseUrl + "/api/articles/${articleId}",
    type: "PUT",
    data: JSON.stringify(article),
    contentType: "application/json",
    dataType: 'json',

    success: function (result) {
        customAlert("success", "Успіх", 'Article "' + article.name + '" was updated');
        $("#create-article-form").find("input, select, textarea, button").prop('disabled', true);
        setTimeout(function() { window.location.href = "${pageContext.request.contextPath}/"; }, 1000);
    },

    error: function (result) {
        customAlert("danger", "Помилка", 'Article "' + article.name + '" wasn''t updated');
    }
});]', TO_DATE('30/12/2020', 'DD/MM/YYYY'), 78);

INSERT INTO comments VALUES (comments_seq.nextval/*1*/, 1/*C++ - Hello world application*/, 1, NULL, 1/*John Doe*/, 'Welcome!', SYSDATE);
INSERT INTO comments VALUES (comments_seq.nextval/*2*/, 1/*C++ - Hello world application*/, 2, 1, 2/*Jane Doe*/, 'Thanks for this example', SYSDATE);
INSERT INTO comments VALUES (comments_seq.nextval/*3*/, 1/*C++ - Hello world application*/, 3, 2, 1/*John Doe*/, ':)', SYSDATE);
COMMIT;
