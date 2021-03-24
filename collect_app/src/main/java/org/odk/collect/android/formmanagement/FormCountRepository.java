package org.odk.collect.android.formmanagement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.odk.collect.android.database.DatabaseInstancesRepository;
import org.odk.collect.android.instances.Instance;
import org.odk.collect.android.instances.InstancesRepository;

public class FormCountRepository {

    private final MutableLiveData<Integer> finalized = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> unsent = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> sent = new MutableLiveData<>(0);

    public LiveData<Integer> getUnsent() {
        update();
        return unsent;
    }

    public LiveData<Integer> getFinalized() {
        update();
        return finalized;
    }

    public LiveData<Integer> getSent() {
        update();
        return sent;
    }

    public void update() {
        InstancesRepository instancesRepository = new DatabaseInstancesRepository();

        int finalizedInstances = instancesRepository.getCountByStatus(Instance.STATUS_COMPLETE, Instance.STATUS_SUBMISSION_FAILED);
        int sentInstances = instancesRepository.getCountByStatus(Instance.STATUS_SUBMITTED);
        int unsentInstances = instancesRepository.getCountByStatus(Instance.STATUS_INCOMPLETE, Instance.STATUS_COMPLETE, Instance.STATUS_SUBMISSION_FAILED);

        finalized.postValue(finalizedInstances);
        sent.postValue(sentInstances);
        unsent.postValue(unsentInstances);
    }
}
