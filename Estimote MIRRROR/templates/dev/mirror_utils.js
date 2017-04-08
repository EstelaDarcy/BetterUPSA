function MirrorClientQueue(mirrorClient, callback) {
    this.queue = []
    var self = this;
    mirrorClient.listen(Mirror.Events.USER_DATA, {
        onconnect: function(ev) {


        },
        ondata: function(ev) {
            for (var i in self.queue) {
                if (self.queue[i].from == ev.from) {
                    self.queue[i].data = ev.data;
                    callback(self.queue);
                    return;
                }
            }
            self.queue.push(ev);
            callback(self.queue);
        },
        ondisconnect: function(ev) {
            for (var i in self.queue) {
                if (self.queue[i].from == ev.from) {
                    self.queue.splice(i, 1);
                    callback(self.queue);
                    return;
                }
            }
        }
    });

}
