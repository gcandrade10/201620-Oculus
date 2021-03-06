(function (ng)
{
    var mod = ng.module("reservasModule");

    mod.controller("reservasCtrl", ['$scope', '$state', '$stateParams', '$http', 'reservasContext', 
        function ($scope, $state, $stateParams, $http, context) {

            // inicialmente el listado de reservas está vacio
            $scope.records = {};
            // carga las reservas
            $http.get(context).then(function(response){
                $scope.records = response.data;
            }, responseError);            
            // el controlador recibió un reservaId ??
            // revisa los parámetros (ver el :reservaId en la definición de la ruta)
            if ($stateParams.reservaId !== null && $stateParams.reservaId !== undefined) {       
                // toma el id del parámetro
                var id = $stateParams.reservaId;
                // obtiene el dato del recurso REST
                $http.get(context + "/" + id)
                    .then(function (response) {
                        // $http.get es una promesa
                        // cuando llegue el dato, actualice currentRecord
                        $scope.currentRecord = response.data;
                    }, responseError);

            // el controlador no recibió un reservaId
            } else
            {
                // el registro actual debe estar vacio
                $scope.currentRecord = {
                    id: undefined /*Tipo Long. El valor se asigna en el backend*/,
                    name: '' /*Tipo String*/
                };
                $scope.alerts = [];
            }
            this.saveRecord = function (id) {
                var currentRecord = $scope.currentRecord;
                // si el id es null, es un registro nuevo, entonces lo crea
                if (id == null) {

                    // ejecuta POST en el recurso REST
                    return $http.post(context, currentRecord)
                        .then(function () {
                            // $http.post es una promesa
                            // cuando termine bien, cambie de estado
                            $state.go('reservasList');
                        }, responseError);
                // si el id no es null, es un registro existente entonces lo actualiza
                } else {
                    // ejecuta PUT en el recurso REST
                    return $http.put(context + "/" + currentRecord.id, currentRecord)
                        .then(function () {
                            // $http.put es una promesa
                            // cuando termine bien, cambie de estado
                            $state.go('reservasList');
                        }, responseError);
                };
            };
            
            this.deleteRecord = function (record) {
                //currentRecord = $scope.currentRecord;
                id = record.id;
                
                // ejecuta DELETE en el recurso REST
                return $http.delete(context + "/" + id, record)
                        .then(function () {
                            $state.reload();
                }, responseError);
                
            };
            
            // -----------------------------------------------------------------
            // Funciones para manejar las fechas
            // -----------------------------------------------------------------

            $scope.popup = {
                opened: false
            };
            $scope.dateOptions = {
                dateDisabled: disabled,
                formatYear: 'yy',
                maxDate: new Date(2020, 5, 22),
                minDate: new Date(),
                startingDay: 1
            };

            this.today = function () {
                $scope.dt = new Date();
            };
            this.today();

            this.clear = function () {
                $scope.dt = null;
            };
            this.setDate = function (year, month, day) {
                $scope.dt = new Date(year, month, day);
            };

            this.open = function () {
                $scope.popup.opened = true;
            };

            function disabled(data) {
                var date = data.date,
                        mode = data.mode;
                return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
            }

            
            // -----------------------------------------------------------------
            // Funciones para manejar los mensajes en la aplicación
            // -----------------------------------------------------------------
            //Alertas
            this.closeAlert = function (index) {
                $scope.alerts.splice(index, 1);
            };

            // Función showMessage: Recibe el mensaje en String y su tipo con el fin de almacenarlo en el array $scope.alerts.
            function showMessage(msg, type) {
                var types = ["info", "danger", "warning", "success"];
                if (types.some(function (rc) {
                    return type === rc;
                })) {
                    $scope.alerts.push({type: type, msg: msg});
                }
            }

            this.showError = function (msg) {
                showMessage(msg, "danger");
            };

            this.showSuccess = function (msg) {
                showMessage(msg, "success");
            };

            var self = this;
            function responseError(response) {

                self.showError(response.data);
            }
        }]);

})(window.angular);