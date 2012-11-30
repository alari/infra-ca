exports = this

m = angular.module("ca.app", ['ca.CreativeAtom', 'ca.CreativeChain', 'ca.ui', 'ca.base'])
#root page basics
m.controller "RootCtr", ['$scope', '$rootScope', ($scope, $rootScope)->
  $rootScope.corePiles = [
    {title: "test"},
    {title: "other"}
  ]

  $scope.title = "test"
  $rootScope.title = "root test"


]

#layout basics
m.controller "LayoutCtr", ['$scope', '$rootScope', 'caTemplates', ($scope, $rootScope, caTemplates)->
  $scope.tpls = caTemplates
]

# Authentication bean
m.service "authService", ($http)->
  @isAuthenticated = false
  @username = null

  @respond = (data, callback)=>
    @isAuthenticated = data.isAuthenticated
    @username = data.username
    callback(data) if callback

  @checkAuth = (callback)=>
    $http.get("/authApi/checkAuth").success (data)=>
      @respond(data, callback)

  @signUp = (credentials, callback)=>
    $http.post("/authApi/signUp", credentials).success (data)->
      @respond(data, callback)

  @signOut = (callback)=>
    $http.post("/authApi/signOut").success (data)=>
      @respond(data, callback)

  @signIn = (credentials, callback)=>
    $http.post("/authApi/signIn", credentials).success (data)=>
      @respond(data, callback)

# Auth control basics
m.controller "AuthCtr", ['$scope', '$rootScope', 'authService', ($scope, $rootScope, authService)->
  $scope.isAuthenticated = false

  authService.checkAuth ->
    $scope.isAuthenticated = authService.isAuthenticated

  $scope.signOut = ->
    authService.signOut (data)->
      if(!data.isAuthenticated)
        window.location.reload()
]

# Sign upper / registration controller
m.controller "SignUpCtr", ['$scope', 'authService', ($scope, authService)->
  $scope.signUp = ->
    authService.signUp {username: $scope.username, password: $scope.password}, (data)->
      window.location.reload()
]

# Sign in controller
m.controller "SignInCtr", ['$scope', 'authService', ($scope, authService)->
  $scope.signIn = ->
    authService.signIn {username: $scope.username, password: $scope.password, rememberMe: $scope.rememberMe}, (data)->
      window.location.reload()
]