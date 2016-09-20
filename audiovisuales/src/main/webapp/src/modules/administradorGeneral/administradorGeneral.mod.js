(function (ng) 
{
    var mod = ng.module("administradorGeneralModule", ["ngMessages"]);
    mod.constant("administradorGeneralContext", "api/administradorGeneral");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) 
        {
            var basePath = 'src/modules/administradorGeneral/';
            $urlRouterProvider.otherwise("/adminGeneralReservasCanceladas");
     
            $stateProvider.state('adminGeneralReservasCanceladas', 
            {
                url: '/administradorGeneral/reservasCanceladas',
                views: {
                    'mainView': {
                        
                        controller: 'administradorGeneralCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'administradorGeneral.search.html'
                    }
                }
            }).state('adminGeneralReservasPendientes', {
                url: '/administradorGeneral/reservasPendientes',
                views: {
                    'mainView': {   
                        controller: 'administradorGeneralCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'administradorGeneral.search.html'
                    }
                }

            });
        }]);
})(window.angular);